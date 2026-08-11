package com.razorpay;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApiUtilsTest {

  @Test
  public void getMediaType_Jpg_ReturnsImageJpeg() {
    assertEquals("image/jpeg", ApiUtils.getMediaType("document.jpg"));
    assertEquals("image/jpeg", ApiUtils.getMediaType("doc.JPG"));
  }

  @Test
  public void getMediaType_Jpeg_ReturnsImageJpeg() {
    assertEquals("image/jpeg", ApiUtils.getMediaType("photo.jpeg"));
    assertEquals("image/jpeg", ApiUtils.getMediaType("photo.JPEG"));
  }

  @Test
  public void getMediaType_Png_ReturnsImagePng() {
    assertEquals("image/png", ApiUtils.getMediaType("image.png"));
    assertEquals("image/png", ApiUtils.getMediaType("image.PNG"));
  }

  @Test
  public void getMediaType_Jfif_ReturnsImageJpeg() {
    assertEquals("image/jpeg", ApiUtils.getMediaType("photo.jfif"));
  }

  @Test
  public void getMediaType_Pdf_ReturnsApplicationPdf() {
    assertEquals("application/pdf", ApiUtils.getMediaType("invoice.pdf"));
    assertEquals("application/pdf", ApiUtils.getMediaType("report.PDF"));
  }

  @Test
  public void getMediaType_UnknownExtension_ReturnsOctetStream() {
    assertEquals("application/octet-stream", ApiUtils.getMediaType("file.xyz"));
    assertEquals("application/octet-stream", ApiUtils.getMediaType("data.json"));
  }

  @Test
  public void getMediaType_NoExtension_ReturnsOctetStream() {
    assertEquals("application/octet-stream", ApiUtils.getMediaType("README"));
  }

  @Test
  public void getMediaType_TrailingDot_ReturnsOctetStream() {
    assertEquals("application/octet-stream", ApiUtils.getMediaType("file."));
  }
}
