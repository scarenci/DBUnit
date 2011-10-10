package br.com.DBUnit;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.DBUnit.domain.entity.Terceiro;
import br.com.DBUnit.domain.repository.TerceiroRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext_test.xml"})
public class HSQLDBUnitTest {

	private TerceiroRepository terceiroRepository;
	
	private Terceiro terceiroValido;
	
	@Before
	public void init(){
		terceiroValido = new Terceiro();
		terceiroValido.setNome("Rafael Scarenci Detoni");
		terceiroValido.setCpf(33443279848L);
		terceiroValido.setRg(412409069L);		
	}
	
	@Test
	public void deveSalvarUmTerceiroValido(){
		Boolean salvar = terceiroRepository.saveOrUpdate(terceiroValido);
		Assert.assertTrue(salvar);
	}
	
	@Test
	public void deveBuscarUmTerceiroValido(){
		deveSalvarUmTerceiroValido();
		Terceiro terceiro = terceiroRepository.get(1L);
		Assert.assertTrue(terceiro != null);
	}

	@Test
	public void deveBuscarUmaListaDeTerceiroValido(){
		deveSalvarUmTerceiroValido();
		deveSalvarUmTerceiroValido();
		List<Terceiro> terceirosValidos = terceiroRepository.findAll();
		Assert.assertTrue(!terceirosValidos.isEmpty());
	}
	
	@Test
	public void deveRemoverUmTerceiroValido(){
		Boolean deletar = terceiroRepository.delete(terceiroValido);
		Assert.assertTrue(deletar);
	}
	
	@Autowired
	public void setTerceiroRepository(TerceiroRepository terceiroRepository) {
		this.terceiroRepository = terceiroRepository;
	}
}
