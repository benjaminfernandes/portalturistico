var Portal = Portal || {};

Portal.MaskCep = (function() {
	
	function MaskCep() {
		this.inputCep = $('.js-cep');
	}
	
	MaskCep.prototype.enable = function() {
		this.inputCep.mask('00.000-000');
	}
	
	return MaskCep;
	
}());

Portal.MaskPhoneNumber = (function(){
	
	function MaskPhoneNumber(){
		this.inputPhone = $('.js-phone-number');
		this.inputCelPhone = $('.js-cel-number');
	}
	
	MaskPhoneNumber.prototype.enable = function(){
		this.inputPhone.mask('(00) 0000-0000');
		this.inputCelPhone.mask('(00) 00000-0000');
	}
	
	return MaskPhoneNumber;
	
}());

$(function(){
	var maskCep = new Portal.MaskCep();
	maskCep.enable();
	
	var maskPhoneNumber = new Portal.MaskPhoneNumber();
	maskPhoneNumber.enable();
});