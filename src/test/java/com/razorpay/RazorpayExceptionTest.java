package com.razorpay;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RazorpayExceptionTest {

  @Test
  public void constructorWithMessage_SetsMessageOnly() {
    RazorpayException ex = new RazorpayException("test message");
    assertEquals("test message", ex.getMessage());
    assertNull(ex.getStatusCode());
    assertNull(ex.getCode());
    assertNull(ex.getDescription());
  }

  @Test
  public void constructorWithMessageAndStatusCode_SetsBoth() {
    RazorpayException ex = new RazorpayException("Server error", 500);
    assertEquals("Server error", ex.getMessage());
    assertEquals(Integer.valueOf(500), ex.getStatusCode());
    assertNull(ex.getCode());
    assertNull(ex.getDescription());
  }

  @Test
  public void constructorWithFullParams_SetsAllFields() {
    RazorpayException ex = new RazorpayException("BAD_REQUEST_ERROR:Invalid payment id",
        400, "BAD_REQUEST_ERROR", "Invalid payment id");
    assertEquals("BAD_REQUEST_ERROR:Invalid payment id", ex.getMessage());
    assertEquals(Integer.valueOf(400), ex.getStatusCode());
    assertEquals("BAD_REQUEST_ERROR", ex.getCode());
    assertEquals("Invalid payment id", ex.getDescription());
  }

  @Test
  public void constructorWithCause_PreservesLegacyBehavior() {
    Exception cause = new RuntimeException("underlying");
    RazorpayException ex = new RazorpayException("wrapped", cause);
    assertEquals("wrapped", ex.getMessage());
    assertEquals(cause, ex.getCause());
    assertNull(ex.getStatusCode());
    assertNull(ex.getCode());
    assertNull(ex.getDescription());
  }
}
