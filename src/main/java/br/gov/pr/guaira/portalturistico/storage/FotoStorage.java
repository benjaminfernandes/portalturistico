package br.gov.pr.guaira.portalturistico.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public final String THUMBNAIL_PREFIX = "thumbnail.";
	
	public String salvarTemporariamente(MultipartFile file);

	public byte[] recuperarFotoTemporaria(String nome);

	public void salvar(String foto);

	public byte[] recuperar(String nome);

	public void excluirFoto(String foto);
	
	public byte[] recuperarThumbnail(String fotoCerveja);
	
	public void salvarFotoCapa(String foto);
	
	public void salvarFotoGaleria(String foto);
}
