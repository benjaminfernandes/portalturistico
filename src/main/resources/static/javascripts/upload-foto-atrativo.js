var Portal = Portal || {};

Portal.UploadFotoAtrativo = (function(){
	
	function UploadFotoAtrativo(){
		this.inputFotoChamada = $('input[name=fotoChamada]');
		this.inputContentTypeChamada = $('input[name=contentTypeChamada]');
		this.novaFotoChamada = $('input[name=novaFotoChamada]');
		this.htmlFotoChamadaTemplate = $('#foto-principal').html();
		this.template = Handlebars.compile(this.htmlFotoChamadaTemplate);
        this.containerFotoChamada = $('.js-container-foto-chamada');
        this.uploadDropChamada = $('#upload-drop-Chamada');
    	
	}
	
	UploadFotoAtrativo.prototype.iniciar = function (){
		var settings = {
				type: 'json',
				filelimit: 1,
				allow: '*.(jpg|jpeg|png)',
				action: this.containerFotoChamada.data('url-fotos'),
				complete: onUploadCompleto.bind(this)
	}
		
		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop(this.uploadDropChamada, settings);
		
		if(this.inputFotoChamada.val()){
			renderizarFoto.call(this, {nome: this.inputFotoChamada.val(), contentype: this.inputContentTypeChamada.val()});
		}
	}
	
	function onUploadCompleto(resposta){
		this.novaFotoChamada.val('true');
		renderizarFoto.call(this, resposta);
	}
	
	function renderizarFoto(resposta){
		this.inputFotoChamada.val(resposta.nome);
		this.inputContentTypeChamada.val(resposta.contentType);
		
		this.uploadDropChamada.addClass('hidden');
		
		var foto = '';
		if(this.novaFotoChamada.val() == 'true'){
			foto = 'temp/';
		}
		foto += resposta.nome;
		
		var htmlFotoPrincipal = this.template({foto: foto});
        this.containerFotoChamada.append(htmlFotoPrincipal);
        
        $('.js-remove-foto').on('click', onRemoverFoto.bind(this));
	}
	
	function onRemoverFoto(){
		$('.js-foto-principal').remove();
    	this.uploadDropChamada.removeClass('hidden');
    	this.inputFotoChamada.val('');
    	this.inputContentTypeChamada.val('');
    	this.novaFotoChamada.val('false');
	}
	
	return UploadFotoAtrativo;
	
})();

$(function(){
	var uploadFoto = new Portal.UploadFotoAtrativo();
	uploadFoto.iniciar();
});