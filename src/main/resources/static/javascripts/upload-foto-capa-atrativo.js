var Portal = Portal || {};

Portal.UploadFotoCapaAtrativo = (function(){
	
	function UploadFotoCapaAtrativo(){
		this.inputFotoCapa = $('input[name=fotoCapa]');
		this.inputContentTypeCapa = $('input[name=contentTypeCapa]');
		this.novaFotoCapa = $('input[name=novaFotoCapa]');
		this.htmlFotoCapaTemplate = $('#foto-capa').html();
		this.template = Handlebars.compile(this.htmlFotoCapaTemplate);
        this.containerFotoCapa = $('.js-container-foto-capa');
        this.uploadDropCapa = $('#upload-drop-capa');
    	
	}
	
	UploadFotoCapaAtrativo.prototype.iniciar = function (){
		var settings = {
				type: 'json',
				filelimit: 1,
				allow: '*.(jpg|jpeg|png)',
				action: this.containerFotoCapa.data('url-fotos'),
				complete: onUploadCompleto.bind(this)
		}
		
		UIkit.uploadSelect($('#upload-select-capa'), settings);
		UIkit.uploadDrop(this.uploadDropCapa, settings);
		
		if(this.inputFotoCapa.val()){
			renderizarFoto.call(this, {nome: this.inputFotoCapa.val(), contentype: this.inputContentTypeCapa.val()});
		}
	}
	
	function onUploadCompleto(resposta){
		this.novaFotoCapa.val('true');
		renderizarFoto.call(this, resposta);
	}
	
	function renderizarFoto(resposta){
		this.inputFotoCapa.val(resposta.nome);
		this.inputContentTypeCapa.val(resposta.contentType);
		
		this.uploadDropCapa.addClass('hidden');
		
		var foto = '';
		if(this.novaFotoCapa.val() == 'true'){
			foto = 'temp/';
		}
		foto += resposta.nome;
		
		var htmlFotoPrincipal = this.template({foto: foto});
        this.containerFotoCapa.append(htmlFotoPrincipal);
        
        $('.js-remove-foto-capa').on('click', onRemoverFoto.bind(this));
	}
	
	function onRemoverFoto(){
		$('.js-foto-capa').remove();
    	this.uploadDropCapa.removeClass('hidden');
    	this.inputFotoCapa.val('');
    	this.inputContentTypeCapa.val('');
    	this.novaFotoCapa.val('false');
	}
	
	return UploadFotoCapaAtrativo;
	
})();

$(function(){
	var uploadFoto = new Portal.UploadFotoCapaAtrativo();
	uploadFoto.iniciar();
});