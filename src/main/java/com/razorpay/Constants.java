package com.razorpay;


public class Constants {

  // API constants
  static final String SCHEME = "https";
  static final String HOSTNAME = "api.razorpay.com";
  static final Integer PORT = 443;
  static final String VERSION = "v1";

  static final String AUTH_HEADER_KEY = "Authorization";
  static final String USER_AGENT = "User-Agent";

  // API URI
  static final String PAYMENT_GET = "payments/%s";
  static final String PAYMENT_LIST = "payments";
  static final String PAYMENT_CAPTURE = "payments/%s/capture";
  static final String PAYMENT_REFUND = "payments/%s/refund";
  static final String PAYMENT_TRANSFER_CREATE = "payments/%s/transfers";
  static final String PAYMENT_TRANSFER_GET = "payments/%s/transfers";
  static final String PAYMENT_BANK_TRANSFER_GET = "payments/%s/bank_transfer";
  static final String PAYMENT_EDIT = "payments/%s";
  static final String PAYMENT_OTP_GENERATE = "payments/%s/otp_generate";
  static final String PAYMENT_OTP_SUBMIT = "payments/%s/otp/submit";
  static final String PAYMENT_OTP_RESEND = "payments/%s/otp/resend";
  static final String FETCH_CARD_DETAILS = "payments/%s/card";
  static final String FETCH_DOWNTIME_LIST = "payments/downtimes";
  static final String FETCH_DOWNTIME_GET = "payments/downtimes";
  static final String PAYMENT_JSON_CREATE = "payments/create/json";
  static final String PAYMENT_RECURRING = "payments/create/recurring";
  static final String PAYMENT_CREATE_UPI = "payments/create/upi";
  static final String VALIDATE_VPA = "payments/validate/vpa";

  static final String FETCH_PAYMENT_METHODS = "methods";

  static final String PAYMENTLINK_CREATE = "payment_links";
  static final String PAYMENTLINK_LIST = "payment_links";
  static final String PAYMENTLINK_GET = "payment_links/%s";
  static final String PAYMENTLINK_EDIT = "payment_links/%s";
  static final String PAYMENTLINK_CANCEL = "payment_links/%s/cancel";
  static final String PAYMENTLINK_NOTIFYBY = "payment_links/%s/notify_by/%s";

  static final String PAYMENT_REFUND_LIST = "payments/%s/refunds";
  static final String PAYMENT_REFUND_GET = "payments/%s/refunds/%s";

  static final String QRCODE_CREATE = "payments/qr_codes";
  static final String QRCODE_FETCH = "payments/qr_codes/%s";
  static final String QRCODE_CLOSE = "payments/qr_codes/%s/close";
  static final String QRCODE_LIST = "payments/qr_codes";
  static final String QRCODE_FETCH_PAYMENT = "payments";

  static final String REFUND_GET = "refunds/%s";
  static final String REFUND_LIST = "refunds";
  static final String REFUND_CREATE = "refunds";

  static final String SETTLEMENTS = "settlements";
  static final String SETTLEMENT = "settlements/%s";
  static final String SETTLEMENTS_REPORTS = "settlements/recon/combined";
  static final String SETTLEMENTS_INSTANT = "settlements/ondemand";
  static final String SETTLEMENT_INSTANT = "settlements/ondemand/%s";

  static final String REFUND = "refunds/%s";
  static final String REFUNDS = "refunds";
  static final String REFUND_MULTIPLE = "payments/%s/refunds";

  static final String FUND_ACCOUNT_CREATE = "fund_accounts";
  static final String FUND_ACCOUNT_FETCH = "fund_accounts/%s";
  static final String FUND_ACCOUNT_LIST = "fund_accounts";

  static final String ORDER_CREATE = "orders";
  static final String ORDER_GET = "orders/%s";
  static final String ORDER_EDIT = "orders/%s";
  static final String ORDER_LIST = "orders";
  static final String ORDER_PAYMENT_LIST = "orders/%s/payments";

  static final String INVOICE_CREATE = "invoices";
  static final String INVOICE_GET = "invoices/%s";
  static final String INVOICE_LIST = "invoices";
  static final String INVOICE_CANCEL = "invoices/%s/cancel";
  static final String INVOICE_ISSUE = "invoices/%s/issue";
  static final String INVOICE_NOTIFY = "invoices/%s/notify_by/%s";

  static final String ITEMS = "items";
  static final String ITEM = "items/%s";

  static final String CARD_GET = "cards/%s";

  static final String CUSTOMER_CREATE = "customers";
  static final String CUSTOMER_GET = "customers/%s";
  static final String CUSTOMER_EDIT = "customers/%s";
  static final String CUSTOMER_LIST = "customers";

  static final String TOKEN_LIST = "customers/%s/tokens";
  static final String TOKEN_GET = "customers/%s/tokens/%s";
  static final String TOKEN_DELETE = "customers/%s/tokens/%s";

  static final String TRANSFER_CREATE = "transfers";
  static final String TRANSFER_GET = "transfers/%s";
  static final String TRANSFER_EDIT = "transfers/%s";
  static final String TRANSFER_LIST = "transfers";
  static final String TRANSFER_REVERSAL_CREATE = "transfers/%s/reversals";

  static final String PLAN_CREATE = "plans";
  static final String PLAN_GET = "plans/%s";
  static final String PLAN_LIST = "plans";

  static final String SUBSCRIPTION_CREATE = "subscriptions";
  static final String SUBSCRIPTION = "subscriptions/%s";
  static final String SUBSCRIPTION_LIST = "subscriptions";
  static final String SUBSCRIPTION_CANCEL = "subscriptions/%s/cancel";
  static final String SUBSCRIPTION_ADDON_CREATE = "subscriptions/%s/addons";

  static final String SUBSCRIPTION_PENDING_UPDATE = "subscriptions/%s/retrieve_scheduled_changes";
  static final String SUBSCRIPTION_CANCEL_SCHEDULED_UPDATE = "subscriptions/%s/cancel_scheduled_changes";
  static final String PAUSE_SUBSCRIPTION = "subscriptions/%s/pause";
  static final String RESUME_SUBSCRIPTION = "subscriptions/%s/resume";
  static final String SUBSCRIPTION_OFFER = "subscriptions/%s/%s";
  static final String SUBSCRIPTION_REGISTRATION_LINK = "subscription_registration/auth_links";

  static final String ADDON_GET = "addons/%s";
  static final String ADDON_DELETE = "addons/%s";
  static final String ADDON_LIST = "addons";

  static final String VIRTUAL_ACCOUNT_CREATE = "virtual_accounts";
  static final String VIRTUAL_ACCOUNT_GET = "virtual_accounts/%s";
  static final String VIRTUAL_ACCOUNT_LIST = "virtual_accounts";
  static final String VIRTUAL_ACCOUNT_EDIT = "virtual_accounts/%s";
  static final String VIRTUAL_ACCOUNT_CLOSE = "virtual_accounts/%s/close";
  static final String VIRTUAL_ACCOUNT_PAYMENTS = "virtual_accounts/%s/payments";
  static final String VIRTUAL_ACCOUNT_RECEIVERS = "virtual_accounts/%s/receivers";
  static final String VIRTUAL_ACCOUNT_ALLOWEDPAYERS = "virtual_accounts/%s/allowed_payers";
  static final String VIRTUAL_ACCOUNT_DELETE_ALLOWEDPAYERS = "virtual_accounts/%s/allowed_payers/%s";
}