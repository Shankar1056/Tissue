package bigappcompany.com.tissu.model;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 01 Jul 2017 at 2:46 PM
 */

public class PaymentMethodModel {
	
	private String paymentmehtod_id;
	
	public String getPaymentmehtod_id() {
		return paymentmehtod_id;
	}
	
	public String getPaymentmethods_name() {
		return paymentmethods_name;
	}
	
	public String getPaymentmethod_type() {
		return paymentmethod_type;
	}
	
	public String getPaymentmethod_logo() {
		return paymentmethod_logo;
	}
	
	private String paymentmethods_name;
	private String paymentmethod_type;
	private String paymentmethod_logo;
	public PaymentMethodModel(String paymentmehtod_id, String paymentmethods_name, String paymentmethod_type,
	                          String paymentmethod_logo)
	{
		this.paymentmehtod_id = paymentmehtod_id;
		this.paymentmethods_name = paymentmethods_name;
		this.paymentmethod_type = paymentmethod_type;
		this.paymentmethod_logo = paymentmethod_logo;
	}
}
