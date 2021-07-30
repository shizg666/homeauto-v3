package com.landleaf.homeauto.common.util;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @Author Lokiy
 * @Date 2021/7/28 15:47
 * @Description 压缩解压
 */
public class DeflaterUtil {

    /**
     * 压缩字符串
     * @param unzipString 待压缩字符串
     * @return 压缩后的字符串
     */
    public static String zipString(String unzipString) {
        //使用指定的压缩级别创建一个新的压缩器。
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        //设置压缩输入数据。
        deflater.setInput(unzipString.getBytes());
        //当被调用时，表示压缩应该以输入缓冲区的当前内容结束。
        deflater.finish();
        final byte[] bytes = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        while (!deflater.finished()) {
        //压缩输入数据并用压缩数据填充指定的缓冲区。
            int length = deflater.deflate(bytes);
            outputStream.write(bytes, 0, length);
        }
        //关闭压缩器并丢弃任何未处理的输入。
        deflater.end();
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }


    /**
     * 解压字符串
     * @param zipString 待解压字符串
     * @return 解压后的字符串
     */
    public static String unzipString(String zipString) {
        byte[] decode = Base64.getDecoder().decode(zipString);
        //创建一个新的解压缩器 https://www.yiibai.com/javazip/javazip_inflater.html
        Inflater inflater = new Inflater();
     //设置解压缩的输入数据。
        inflater.setInput(decode);

        final byte[] bytes = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        try {
            //finished() 如果已到达压缩数据流的末尾，则返回true。
            while (!inflater.finished()) {
            //将字节解压缩到指定的缓冲区中。
                int length = inflater.inflate(bytes);
                outputStream.write(bytes, 0, length);
            }
        } catch (DataFormatException e) {
            e.printStackTrace();
            return null;
        } finally {
            //关闭解压缩器并丢弃任何未处理的输入。
            inflater.end();
        }
        return outputStream.toString();
    }

}
