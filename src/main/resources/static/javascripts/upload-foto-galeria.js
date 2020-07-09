var Portal = Portal || {};

Portal.UploadFotoGaleria = (function(){
	
	function UploadFotoGaleria(){
		this.htmlFotoPrincipalTemplate = $('#foto-galeria').html();
		this.template = Handlebars.compile(this.htmlFotoPrincipalTemplate);
        this.containerFotoPrincipal = $('.js-container-foto-galeria');
        this.uploadDrop = $('#upload-drop-galeria');
        this.fotosAdicionadas = new Array();
        this.fotosGaleria = $('input[name=fotosGaleria]');
	}
	
	UploadFotoGaleria.prototype.iniciar = function (){
		var settings = {
				type: 'json',
				filelimit: 10,
				allow: '*.(jpg|jpeg|png)',
				action: this.containerFotoPrincipal.data('url-fotos'),
				complete: onUploadCompleto.bind(this)
		}
		
		UIkit.uploadSelect($('#upload-select-galeria'), settings);
		UIkit.uploadDrop(this.uploadDrop, settings);
		
		if(this.fotosGaleria.val()){
			var obj = JSON.parse(this.fotosGaleria.val());
			
			for(var i = 0; i < obj.length; i++){
				renderizarFoto.call(this, {nome: obj[i].nome, contentype: obj[i].contentType, fotoNova: obj[i].fotoNova});
			}
		}
	}
	
	function onUploadCompleto(resposta){
		renderizarFoto.call(this, resposta);
	}
	
	function renderizarFoto(resposta){
		
		var foto = '';
		if(resposta.fotoNova == true){
			foto = 'temp/';
		}

		foto += resposta.nome;
		codigo = Math.floor(Math.random() * 1000 + 1);
		
		var htmlFotoPrincipal = this.template({foto: foto, codigo: codigo});
        this.containerFotoPrincipal.append(htmlFotoPrincipal);
        
        this.fotosAdicionadas.push(resposta);
		this.fotosGaleria.val(JSON.stringify(this.fotosAdicionadas));
        
        $('#'+codigo).on('click', onRemoverFoto.bind(this, codigo, resposta));
	}
	
	function onRemoverFoto(codigo, resposta){
		$('#'+codigo).remove();
		
		this.fotosAdicionadas.splice(this.fotosAdicionadas.indexOf(resposta.nome),1);
		this.fotosGaleria.val(JSON.stringify(this.fotosAdicionadas));
	}
	return UploadFotoGaleria;
	
})();

$(function(){
	var uploadFoto = new Portal.UploadFotoGaleria();
	uploadFoto.iniciar();
});