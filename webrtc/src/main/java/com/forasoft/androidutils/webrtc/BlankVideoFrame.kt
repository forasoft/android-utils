package com.forasoft.androidutils.webrtc

import org.webrtc.JavaI420Buffer
import org.webrtc.VideoFrame
import java.nio.ByteBuffer

/** This is a two half-bytes having half of their maximum value
 *
 * The UV parts of YUV420 are half-byte in size, hence they belong to the range [0b0000, 0b1111].
 * We need the byte of two half-bytes which are in the middle of their range
 */
private val BLACK_UV_BYTE = 0b10001000.toUByte().toByte()

/**
 * Returns a blank (fully black) [VideoFrame]
 *
 * @param width the width of the frame in pixels
 * @param height the height of the frame in pixels
 * @param rotation the rotation of the frame in degrees, multiple of 90
 */
fun getBlankVideoFrame(width: Int, height: Int, rotation: Int): VideoFrame {
    val chromaHeight: Int = (height + 1) / 2
    val strideUV: Int = (width + 1) / 2

    val yBuffer = ByteBuffer.allocateDirect(width * height)
    repeat(width * height) { yBuffer.put(0.toUByte().toByte()) }
    yBuffer.rewind()

    val uBuffer = ByteBuffer.allocateDirect(strideUV * chromaHeight)
    repeat(strideUV * chromaHeight) { uBuffer.put(BLACK_UV_BYTE) }
    uBuffer.rewind()

    val vBuffer = ByteBuffer.allocateDirect(strideUV * chromaHeight)
    repeat(strideUV * chromaHeight) { vBuffer.put(BLACK_UV_BYTE) }
    vBuffer.rewind()

    return VideoFrame(
        JavaI420Buffer.wrap(
            width,
            height,
            yBuffer,
            width,
            uBuffer,
            strideUV,
            vBuffer,
            strideUV,
            null
        ),
        rotation,
        System.currentTimeMillis()
    )
}
