# Changelog

## Version 1.4.8 - 2024-10-23
* Added support for fetch methods on payments

## Version 1.4.7 - 2024-09-16
Fix: Updated json dependency to fix security vulnerability

## Version 1.4.6 - 2024-04-02
feat: Added new API endpoints
* Added support for addBankAccount, deleteBankAccount, requestEligibilityCheck & fetchEligibility on customer
* Added support for Dispute
* Added support for uploadStakeholderDoc, fetchStakeholderDoc on stakeholder
* Added support for uploadAccountDoc, fetchAccountDoc on account
* Added support for Document
* Added support for fetch all IINs Supporting native otps & fetch all IINs with business sub-type using fetchList
* Added support for viewRtoReview & editFulfillment on order

## Version 1.4.5 - 2024-02-14
[#302](https://github.com/razorpay/razorpay-java/pull/302)
* Added oauth APIs (getAuthURL, getAccessToken, refreshToken, revokeToken) 
* Added support for access token based authentication mechanism
* Added support for onboarding signature generation
* Doc updated

## Version 1.4.4 - 2023-05-30
[#286](https://github.com/razorpay/razorpay-java/pull/286) [`b6179a5`](https://github.com/razorpay/razorpay-java/commit/b6179a5a3425910a190f0d9e4f5ff0f8e423f6a9) feat: Added new API endpoints

* Added account onboarding API (create, fetch, edit, delete)
* Added stakeholders API (create, fetch, fetchAll, edit)
* Added product configuration API (requestProductConfiguration, fetch, edit, fetchTnc)
* Added webhooks API (create, fetch, fetchAll, edit, delete)
* Added token sharing API (create, fetch, delete, processPaymentOnAlternatePAorPG)

## Version 1.4.3 - 2022-07-07
* Added Third party validation API for Payments (createUpi, validateVpa)
* Added Fetch payment methods API for Payments
* Doc Updated

## Version 1.4.2 - 2022-06-14
* New APIs for Payments (Otp generate, Otp submit, Otp resend)
* Bug fix for delete
* Doc Updated

## Version 1.4.0 - 2022-03-07
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

## Version 1.3.9 - 2020-12-17
* Bug fix for Close Virtual Account

## Version 1.3.8 - 2018-08-22
* Support for cancelling an Invoice

## Version 1.3.7 - 2018-07-01
* More Robust TLS fixes.

## Version 1.3.6 - 2018-06-22
* Forces TLS1.1, 1.2 to be enabled

## Version 1.3.5 - 2018-02-27
* Support for RAW API calls

## Version 1.3.4 - 2017-12-20
* Fix for delete APIs for Tokens and Addons.

## Version 1.3.3 - 2017-12-09
* Support for Delete Customer Token

## Version 1.3.2 - 2017-11-14
* New APIs for Subscription, Addon, Plan, Virtual Account and Bank Transfer

## Version 1.3.1 - 2017-07-31
* Webhook signature verification: The webhook signature verification method was fixed to allow custom secrets for webhooks.
* We also changed the method signature to make it consistent across all SDKs.

## Version 1.2.4 - 2017-06-01
* Support for Transfer APIs

## Version 1.2.1 - 2017-04-10
* Bump version to 1.2.1
