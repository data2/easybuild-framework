package com.data2.easybuild.server.common.seq;

/**
 * @author data2
 * @description
 * @date 2021/1/29 下午5:17
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ThreadLocalRandom;

public class SnowflakeIdWorker {
    private static final Log logger = LogFactory.getLog(SnowflakeIdWorker.class);
    private static final String STR_AT = "@";
    private final long twepoch = 1356969600000L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = 31L;
    private final long maxDatacenterId = 31L;
    private final long sequenceBits = 12L;
    private final long workerIdShift = 12L;
    private final long datacenterIdShift = 17L;
    private final long timestampLeftShift = 22L;
    private final long sequenceMask = 4095L;
    private final long workerId;
    private final long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdWorker() {
        this.datacenterId = getDatacenterId(31L);
        this.workerId = getMaxWorkerId(this.datacenterId, 31L);
    }

    public SnowflakeIdWorker(long workerId, long datacenterId) {
        if ((datacenterId > 31L) || (datacenterId < 0L)) {
            throw new IllegalArgumentException(" datacenterId 必须介于[0,31] ");
        }
        if ((workerId > 31L) || (workerId < 0L)) {
            throw new IllegalArgumentException(" workerId 必须介于[0,31] ");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuilder mpid = new StringBuilder();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtils.isNotBlank(name)) {
            mpid.append(name.split("@")[0]);
        }
        return (mpid.toString().hashCode() & 0xFFFF) % (maxWorkerId + 1L);
    }

    protected static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    id = (0xFF & mac[(mac.length - 1)] | 0xFF00 & mac[(mac.length - 2)] << 8) >> 6;
                    id %= (maxDatacenterId + 1L);
                }
            }
        } catch (Exception e) {
            logger.warn(" getDatacenterId: " + e.getMessage());
        }
        return id;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < this.lastTimestamp) {
            long offset = this.lastTimestamp - timestamp;
            if (offset <= 5L) {
                try {
                    wait(offset << 1);
                    timestamp = timeGen();
                    if (timestamp < this.lastTimestamp) {
                        throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", new Object[]{Long.valueOf(offset)}));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", new Object[]{Long.valueOf(offset)}));
            }
        }
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1L & 0xFFF);
            if (this.sequence == 0L) {
                timestamp = tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = ThreadLocalRandom.current().nextLong(1L, 3L);
        }
        this.lastTimestamp = timestamp;

        return timestamp - 1356969600000L << 22 | this.datacenterId << 17 | this.workerId << 12 | this.sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public String parseId(Long id) {
        if (null == id) {
            return "";
        }
        return String.format("sequence: %d, workerId: %d, clusterId: %d, timestamp: %d\n", new Object[]{Long.valueOf(id.longValue() & 0xFFF),
                Long.valueOf(id.longValue() >> 12 & 0x1F),
                Long.valueOf(id.longValue() >> (int) this.datacenterId & 0x1F),
                Long.valueOf((id.longValue() >> 22) + 1356969600000L)});
    }
}

