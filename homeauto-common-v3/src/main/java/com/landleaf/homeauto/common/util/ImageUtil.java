package com.landleaf.homeauto.common.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Lokiy
 * @date 2018/6/22
 * @description 图片处理帮助类 基于thumbnailator
 * https://blog.csdn.net/wangpeng047/article/details/17610451
 */
public class ImageUtil {

    /**
     * 改变图片大小到某个目录
     * @param source 原目录
     * @param length 长度
     * @param heigh 高度
     * @param keepAspectRatio 是否按照比例
     * @param target 目标
     * @throws IOException io异常
     */
    public static void toSize(String source, int length, int heigh, boolean keepAspectRatio, File target)
            throws IOException {
        Thumbnails.of(source)
                .size(length, heigh)
                .keepAspectRatio(keepAspectRatio)
                .toFile(target);
    }

    /**
     * 改变图片大小到某个目录
     * @param source 原目录
     * @param length 长度
     * @param heigh 高度
     * @param target 目标
     * @throws IOException io异常
     */
    public static void toSize(File source, int length, int heigh, boolean keepAspectRatio, File target)
            throws IOException {
        Thumbnails.of(source)
                .size(length, heigh)
                .keepAspectRatio(keepAspectRatio)
                .toFile(target);
    }

    /**
     * 改变图片大小到某个目录
     * @param source 原目录
     * @param length 长度
     * @param heigh 高度
     * @param target 目标
     * @throws IOException io异常
     */
    public static void toSize(InputStream source, int length, int heigh, boolean keepAspectRatio, File target)
            throws IOException {
        Thumbnails.of(source)
                .size(length, heigh)
                .keepAspectRatio(keepAspectRatio)
                .toFile(target);
    }


    /**
     * 按比例缩放
     * @param source 原文件
     * @param scale 比例
     * @param target 目标文件
     * @throws IOException
     */
    public static void toScale(String source, double scale, File target)
            throws IOException {
        Thumbnails.of(source)
                .scale(scale)
                .toFile(target);
    }

    /**
     * 按比例缩放
     * @param source 原文件
     * @param scale 比例
     * @param target 目标文件
     * @throws IOException
     */
    public static void toScale(File source, double scale, File target)
            throws IOException {
        Thumbnails.of(source)
                .scale(scale)
                .toFile(target);
    }

    /**
     * 按比例缩放
     * @param source 原文件
     * @param scale 比例
     * @param target 目标文件
     * @throws IOException
     */
    public static void toScale(InputStream source, double scale, File target)
            throws IOException {
        Thumbnails.of(source)
                .scale(scale)
                .toFile(target);
    }


    public static void main(String[] args) {
        File source = new File("D:\\data\\source\\abcd.jpg");
        File target = new File("D:\\data\\target\\tt.jpg");
        try {
            toSize(source, 150, 150, true, target);
//            toScale(source, 1, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
