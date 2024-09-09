package com.data2.easybuild.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;
import java.util.function.BiConsumer;

public class CaptchaUtils {
    private static final Integer DEFAULT_FONT_SIZE = Integer.valueOf(24);
    private static final Integer DEFAULT_WIDTH = Integer.valueOf(100);
    private static final Integer DEFAULT_HEIGHT = Integer.valueOf(40);
    private static final Integer DEFAULT_CAPTCHA_COUNT = Integer.valueOf(5);
    private static final Integer DEFAULT_DISTURB_LINE_COUNT = Integer.valueOf(5);
    private static final Font[] RANDOM_FONT = {new Font("nyala", 1, DEFAULT_FONT_SIZE
            .intValue()), new Font("Arial", 1, DEFAULT_FONT_SIZE
            .intValue()), new Font("Bell MT", 1, DEFAULT_FONT_SIZE
            .intValue()), new Font("Credit valley", 1, DEFAULT_FONT_SIZE
            .intValue()), new Font("Impact", 1, DEFAULT_FONT_SIZE
            .intValue())};
    private static final Color[] RANDOM_COLOR = {new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255), new Color(0, 0, 0)};
    private static char[] CAPTCHA_CHARS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static BufferedImage generateCaptchaImage() {
        return generateCaptchaImage(generateCaptchaCode(DEFAULT_CAPTCHA_COUNT), DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_DISTURB_LINE_COUNT);
    }

    public static void generateCaptchaImage(BiConsumer<String, BufferedImage> consumer) {
        String code = generateCaptchaCode(DEFAULT_CAPTCHA_COUNT);
        BufferedImage bufferedImage = generateCaptchaImage(code, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_DISTURB_LINE_COUNT);
        consumer.accept(code, bufferedImage);
    }

    public static void generateCaptchaImage(Integer width, Integer height, BiConsumer<String, BufferedImage> consumer) {
        String code = generateCaptchaCode(DEFAULT_CAPTCHA_COUNT);
        BufferedImage bufferedImage = generateCaptchaImage(code, width, height, DEFAULT_DISTURB_LINE_COUNT);
        consumer.accept(code, bufferedImage);
    }

    public static void generateCaptchaImage(Integer width, Integer height, Integer disturbLineCount, BiConsumer<String, BufferedImage> consumer) {
        String code = generateCaptchaCode(DEFAULT_CAPTCHA_COUNT);
        BufferedImage bufferedImage = generateCaptchaImage(code, width, height, disturbLineCount);
        consumer.accept(code, bufferedImage);
    }

    public static BufferedImage generateCaptchaImage(Integer width, Integer height) {
        return generateCaptchaImage(generateCaptchaCode(DEFAULT_CAPTCHA_COUNT), width, height, DEFAULT_DISTURB_LINE_COUNT);
    }

    public static BufferedImage generateCaptchaImage(String captchaCode, Integer width, Integer height, Integer disturbLineCount) {
        BufferedImage bufferedImage = new BufferedImage(width.intValue(), height.intValue(), 1);
        Graphics2D graphics = bufferedImage.createGraphics();

        Random random = new Random();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width.intValue(), height.intValue());

        Font font = RANDOM_FONT[new SecureRandom().nextInt(RANDOM_FONT.length)];
        graphics.setFont(font);

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < disturbLineCount.intValue(); i++) {
            int xs = random.nextInt(width.intValue());

            int ys = random.nextInt(height.intValue());

            int xe = xs + random.nextInt(width.intValue() / 2);

            int ye = ys + random.nextInt(height.intValue() / 2);

            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);
            graphics.setColor(new Color(red, green, blue));
            graphics.drawLine(xs, ys, xe, ye);
        }
        Color codeColor = RANDOM_COLOR[new SecureRandom().nextInt(RANDOM_COLOR.length)];
        graphics.setColor(codeColor);

        FontMetrics metrics = graphics.getFontMetrics(font);

        int x = (width.intValue() - metrics.stringWidth(captchaCode)) / 2;

        int y = (height.intValue() - metrics.getHeight()) / 2 + metrics.getAscent();
        graphics.drawString(captchaCode, x, y);
        return bufferedImage;
    }

    public static String generateCaptchaCode(Integer count) {
        char[] rands = new char[count.intValue()];
        Random rand = new SecureRandom();
        for (int i = 0; i < count.intValue(); i++) {
            rands[i] = CAPTCHA_CHARS[rand.nextInt(CAPTCHA_CHARS.length)];
        }
        return new String(rands);
    }
}
