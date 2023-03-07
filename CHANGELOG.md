# Changelog

## Version 1.4.3
* Added Third party validation API for Payments (createUpi, validateVpa)
* Added Fetch payment methods API for Payments
* Doc Updated

## Version 1.4.2
* New APIs for Payments (Otp generate, Otp submit, Otp resend)
* Bug fix for delete
* Doc Updated

## Version 1.4.0
* Added Item Api
* Added RegistrationLink Api
* QR code end point Api
* Update, cancel update ,fetch details of a Pending Update, pause, resume subscription & delete offer Api
* Update Customer Api,
* Add Settlement Api,
* Added PaymentLink Api,
* Added issue an invoice, delete invoice for invoice
* Add/Delete TPV Bank Account
* Register emandate and charge first payment together
* PaperNACH/Register NACH and charge first payment together
* Added create recurring payment, fetch card details, card downtime & card downtime by Id , create payment json API's for payment
* Added edit and notify API's for payment links
* Added edit, refund, fetch multiple refund for payment API's for refunds
* Added edit order Api
vFund API's end point
* UPI
* Added Verfiy payment link ,payment & subscription verification
* Added Testcases
vAdded readme files

## Version 1.3.9
* Bug fix for Close Virtual Account

## Version 1.3.8
* Support for cancelling an Invoice

## Version 1.3.7
* More Robust TLS fixes.

## Version 1.3.6
* Forces TLS1.1, 1.2 to be enabled

## Version 1.3.5
* Support for RAW API calls

## Version 1.3.4
* Fix for delete APIs for Tokens and Addons.

## Version 1.3.3
* Support for Delete Customer Token

## Version 1.3.2
* New APIs for Subscription, Addon, Plan, Virtual Account and Bank Transfer

## Version 1.3.1
* Webhook signature verification: The webhook signature verification method was fixed to allow custom secrets for webhooks.
We also changed the method signature to make it consistent across all SDKs.