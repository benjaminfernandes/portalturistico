package br.gov.pr.guaira.portalturistico.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import br.gov.pr.guaira.portalturistico.storage.FotoStorage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {

	private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);

	private Path local;
	private Path localTemporario;

	public FotoStorageLocal() {
		this(getDefault().getPath(System.getenv("HOMEDRIVE"), "/fotos"));
		// DEPOIS DEIXAR GENÉRICO PARA CRIAR PASTAS TANDO NO WINDOWS COMO NO LINUX E IOS

		// PAREI NO 14.7 DO UPLOAD
	}

	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}

	@Override
	public String salvarTemporariamente(MultipartFile file) {
		String novoNome = null;

		if (file != null) {

			novoNome = renomearArquivo(file.getOriginalFilename());
			try {
				file.transferTo(new File(
						this.localTemporario.toAbsolutePath().toString() + getDefault().getSeparator() + novoNome));
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando a foto temporariamente", e);
			}
			return novoNome;
		}
		return novoNome;

	}
	
	@Override
	public byte[] recuperarFotoTemporaria(String nome) {
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo as fotos temporárias", e);
		}
	}
	
	@Override
	public void salvar(String foto) {
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro movendo a foto para o destino final", e);
		}
		
		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(350, 320).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando o thumbnail", e);
		}
	}
	
	@Override
	public void salvarFotoCapa(String foto) {
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro movendo a foto de capa para o destino final", e);
		}
		
		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(950, 600).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando o thumbnail da foto de capa", e);
		}
	}
	
	@Override
	public void salvarFotoGaleria(String foto) {
		
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro movendo a foto da galeria para o destino final", e);
		}
		
		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(950, 620).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando o thumbnail da foto de galeria", e);
		}
	}
	
	@Override
	public byte[] recuperar(String nome) {
		try {
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo as fotos", e);
		}
	}
	
	@Override
	public byte[] recuperarThumbnail(String fotoHospedagem) {
		return recuperar(THUMBNAIL_PREFIX + fotoHospedagem);
	}
	
	@Override
	public void excluirFoto(String foto) {
		
		try {
			Files.deleteIfExists(this.local.resolve(foto));
			Files.deleteIfExists(this.local.resolve(THUMBNAIL_PREFIX+foto));
		} catch (IOException e) {
			logger.warn(String.format("Erro apagando a foto '%s'. Mensagem: %s", foto, e.getMessage()));
		}
	}
	
	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);

			System.out.println("Pasta f: " + this.local);
			System.out.println("Pasta T:" + this.localTemporario);

			if (logger.isDebugEnabled()) {
				logger.debug("Pastas criadas para salvar fotos!");
				logger.debug("Pasta default: " + this.local.toAbsolutePath());
				logger.debug("Pasta temporária " + this.localTemporario.toAbsolutePath());
			}

		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}
	}

	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Nome original %s, novo nome do arquivo %s", nomeOriginal, novoNome));
		}
		return novoNome;
	}

}
