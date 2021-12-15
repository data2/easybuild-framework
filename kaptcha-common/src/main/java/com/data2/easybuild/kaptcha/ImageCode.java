package com.data2.easybuild.kaptcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class ImageCode {

    private CodeEntity codeEntity;
    private BufferedImage bufferedImage;

    ImageCode() {
    }

    ImageCode(String codeId) {
        this.codeEntity = new CodeEntity(codeId);
    }

    public ImageCode createImage() {
        bufferedImage = createBufferedImage();
        //获取当前图片的画笔
        Graphics gps = bufferedImage.getGraphics();
        StringBuilder sb = new StringBuilder();
        //开始画东西
        for (int i = 0; i < 4; i++) {
            String ch = this.getContent();
            sb.append(ch);
            gps.setColor(this.getColor());
            gps.setFont(this.getFont());
            //宽度让其不满图片
            gps.drawString(ch, width / 4 * i, height - 5);
        }
        drawLine(bufferedImage);
        if (Objects.isNull(this.codeEntity)){
            this.codeEntity = new CodeEntity(UUID.randomUUID().toString());
        }
        this.codeEntity.setValidateCode(sb.toString());
        return this;

    }

    private BufferedImage createBufferedImage() {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//内存中创建一张图片
        Graphics gps = img.getGraphics();//获取当前图片的画笔
        gps.setColor(new Color(240, 240, 240));//设置画笔
        gps.fillRect(0, 0, width, height);//填充一个与图片一样大小的矩形（其实就是为了设置背景颜色）
        return img;
    }

    private String getContent() {
        int index = r.nextInt(str.length());
        return str.charAt(index) + "";
    }

    private Font getFont() {
        int index1 = r.nextInt(font.length);
        String name = font[index1];
        int style = r.nextInt(4);
        int index2 = r.nextInt(fontSize.length);
        int size = fontSize[index2];
        return new Font(name, style, size);
    }

    private Color getColor() {
        int R = r.nextInt(256);
        int G = r.nextInt(256);
        int B = r.nextInt(256);
        return new Color(R, G, B);
    }

    private void drawLine(BufferedImage img) {
        Graphics2D gs = (Graphics2D) img.getGraphics();
        gs.setColor(Color.BLACK);
        //设置线的宽度
        gs.setStroke(new BasicStroke(1.0F));
        //横坐标不能超过宽度，纵坐标不能超过高度
        for (int i = 0; i < 5; i++) {
            int x1 = r.nextInt(width);
            int y1 = r.nextInt(height);
            int x2 = r.nextInt(width);
            int y2 = r.nextInt(height);
            gs.drawLine(x1, y1, x2, y2);
        }
    }

    public CodeEntity getCodeEntity() {
        return codeEntity;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    /**
     * for test u can do like this >
     * ImageIO.write(img, "JPEG", new FileOutputStream("F:\\a.jpg"));//保存到硬盘
     *
     * @param out
     * @throws IOException
     */
    public void printImage(OutputStream out) throws IOException {
        ImageIO.write(bufferedImage, "JPEG", out);
    }


    public static final int width = 79;
    public static final int height = 30;
    private Random r = new Random();
    /**
     * 在图片中插入字母和十个数字
     */
    public static final String str = "abcdefghijklmnupqrstuvwxyzABCDEFGHIJKLMNUPQRSTUVWZYZ1234567890";
    public static final String[] font = {"宋体", "华文楷体", "华文隶书", "黑体", "华文新魏"};//字体
    public static final int[] fontSize = {24, 25, 26, 27, 28};
    public static final int[] fontStyle = {0, 1, 2, 3};


}
