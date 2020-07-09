var Portal = Portal || {};

Portal.UploadFoto = (function(){
	
	function UploadFoto(){
		this.inputNomeFoto = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		this.novaFoto = $('input[name=novaFoto]');
		this.htmlFotoPrincipalTemplate = $('#foto-principal').html();
		this.template = Handlebars.compile(this.htmlFotoPrincipalTemplate);
        this.containerFotoPrincipal = $('.js-container-foto-principal');
        this.uploadDrop = $('#upload-drop');
    	
	}
	
	UploadFoto.prototype.iniciar = function (){
		var settings = {
				type: 'json',
				filelimit: 1,
				allow: '*.(jpg|jpeg|png)',
				action: this.containerFotoPrincipal.data('url-fotos'),
				complete: onUploadCompleto.bind(this)
		}
		
		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop(this.uploadDrop, settings);
		
		if(this.inputNomeFoto.val()){
			renderizarFoto.call(this, {nome: this.inputNomeFoto.val(), contentype: this.inputContentType.val()});
		}
	}
	
	function onUploadCompleto(resposta){
		this.novaFoto.val('true');
		renderizarFoto.call(this, resposta);
	}
	
	function renderizarFoto(resposta){
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);
		
		this.uploadDrop.addClass('hidden');
		
		var foto = '';
		if(this.novaFoto.val() == 'true'){
			foto = 'temp/';
		}
		foto += resposta.nome;
		
		var htmlFotoPrincipal = this.template({foto: foto});
        this.containerFotoPrincipal.append(htmlFotoPrincipal);
        
        $('.js-remove-foto').on('click', onRemoverFoto.bind(this));
	}
	
	function onRemoverFoto(){
		$('.js-foto-principal').remove();
    	this.uploadDrop.removeClass('hidden');
    	this.inputNomeFoto.val('');
    	this.inputContentType.val('');
    	this.novaFoto.val('false');
	}
	
	return UploadFoto;
	
})();

$(function(){
	var uploadFoto = new Portal.UploadFoto();
	uploadFoto.iniciar();
});