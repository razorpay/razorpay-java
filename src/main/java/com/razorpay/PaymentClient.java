package com.razorpay;

import java.util.List;

import org.json.JSONObject;

import okhttp3.Response;

public class PaymentClient extends ApiClient {

  private RefundClient refundClient;

  PaymentClient(String auth) {
    super(auth);
    refundClient = new RefundClient(auth);
  }

  public Payment fetch(String id) throws RazorpayException {
    return get(Constants.VERSION, String.format(Constants.PAYMENT_GET, id), null);
  }

  public List<Payment> fetchAll(JSONObject request) throws RazorpayException {
    return getCollection(Constants.VERSION, Constants.PAYMENT_LIST, request);
  }

  public List<Payment> fetchAll() throws RazorpayException {
    return fetchAll(null);
  }

  public Payment capture(String id, JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.PAYMENT_CAPTURE, id), request);
  }

  public Refund refund(String id) throws RazorpayException {
    return refund(id, null);
  }

  public Refund refund(String id, JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.PAYMENT_REFUND, id), request);
  }

  public Refund refund(JSONObject request) throws RazorpayException {
    return refundClient.create(request);
  }

  public Refund fetchRefund(String id, String refundId) throws RazorpayException {
    return get(Constants.VERSION, String.format(Constants.PAYMENT_REFUND_GET, id, refundId), null);
  }

  public Refund fetchRefund(String refundId) throws RazorpayException {
    return refundClient.fetch(refundId);
  }

  public List<Refund> fetchAllRefunds(String id, JSONObject request) throws RazorpayException {
    return getCollection(Constants.VERSION, String.format(Constants.PAYMENT_REFUND_LIST, id), request);
  }

  public List<Refund> fetchAllRefunds(String id) throws RazorpayException {
    return fetchAllRefunds(id, null);
  }

  public List<Refund> fetchAllRefunds(JSONObject request) throws RazorpayException {
    return refundClient.fetchAll(request);
  }

  public List<Transfer> transfer(String id, JSONObject request) throws RazorpayException {
    Response response =
        ApiUtils.postRequest(Constants.VERSION, String.format(Constants.PAYMENT_TRANSFER_CREATE, id), request, auth);
    return processCollectionResponse(response);
  }

  public List<Transfer> fetchAllTransfers(String id) throws RazorpayException {
    return fetchAllTransfers(id, null);
  }

  public List<Transfer> fetchAllTransfers(String id, JSONObject request) throws RazorpayException {
    return getCollection(Constants.VERSION, String.format(Constants.PAYMENT_TRANSFER_GET, id), request);
  }

  public BankTransfer fetchBankTransfers(String id) throws RazorpayException {
    return get(Constants.VERSION, String.format(Constants.PAYMENT_BANK_TRANSFER_GET, id), null);
  }

  public Payment edit(String id, JSONObject request) throws RazorpayException {
    return patch(Constants.VERSION, String.format(Constants.PAYMENT_EDIT, id), request);
  }
  
  public List<Payment> fetchPaymentDowntime() throws RazorpayException {
    return getCollection(Constants.VERSION, Constants.FETCH_DOWNTIME_LIST, null);
  }

  public Payment fetchPaymentDowntimeById(String id) throws RazorpayException {
    return get(Constants.VERSION, String.format(Constants.FETCH_DOWNTIME_GET, id), null);
  }

  public Payment createJsonPayment(JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, Constants.PAYMENT_JSON_CREATE, request);
  }

  public Payment createRecurringPayment(JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, Constants.PAYMENT_RECURRING, request);
  }

  public Payment otpGenerate(String id) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.PAYMENT_OTP_GENERATE, id), null);
  }

  public Payment otpSubmit(String id, JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.PAYMENT_OTP_SUBMIT, id), request);
  }

  public Payment otpResend(String id) throws RazorpayException {
    return post(Constants.VERSION, String.format(Constants.PAYMENT_OTP_RESEND, id), null);
  }
  public Payment createUpi(JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, Constants.PAYMENT_CREATE_UPI, request);
  }
  public Payment validateUpi(JSONObject request) throws RazorpayException {
    return post(Constants.VERSION, Constants.VALIDATE_VPA, request);
  }

  public Payment expandedDetails(String id, JSONObject request) throws RazorpayException {
    return get(Constants.VERSION, String.format(Constants.PAYMENT_GET, id), request);
  }
  public Methods fetchPaymentMethods() throws RazorpayException {
    return get(Constants.VERSION, Constants.METHODS, null);
  }
}
