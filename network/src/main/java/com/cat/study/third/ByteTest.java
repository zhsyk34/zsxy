package com.cat.study.third;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class ByteTest {

	public static void main(String[] args) {
		ByteBuf b1 = Unpooled.copiedBuffer("hello\r".getBytes());
		ByteBuf b2 = Unpooled.copiedBuffer("\nworld".getBytes());

		ByteBuf b3 = b1.duplicate();
		byte b = b3.readByte();
		System.out.println((char) b);
		System.out.println(b1.readerIndex());
		System.out.println(b3.readerIndex());
		int index = b1.forEachByte((v) -> {
			System.out.print((char) v + ",");
			return v != 'l';
		});
		System.out.println(index);

		CompositeByteBuf buf = Unpooled.compositeBuffer(100);
//		System.out.println("size:" + buf.readableBytes());//zero?
		buf.addComponents(b1, b2);
		print(buf);

		/*int i = buf.forEachByte(value -> {
			System.out.println((char) value);
			return value == 'h';
		});
		System.out.println(i);*/

//		System.out.println(buf.forEachByte(ByteProcessor.FIND_CR));
	}

	private static void print(ByteBuf buf) {
		for (int i = 0; i < buf.capacity(); i++) {
			System.out.print((char) buf.getByte(i) + " ");
		}
	}
}
