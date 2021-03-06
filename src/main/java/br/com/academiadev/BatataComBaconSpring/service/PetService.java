package br.com.academiadev.BatataComBaconSpring.service;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.academiadev.BatataComBaconSpring.exception.OperacaoNaoSuportadaException;
import br.com.academiadev.BatataComBaconSpring.exception.PetNaoEncontradoException;
import br.com.academiadev.BatataComBaconSpring.model.Pet;
import br.com.academiadev.BatataComBaconSpring.repository.PetRepository;

@Service
public class PetService {

	@Autowired
	private PetRepository repository;

	/*
	 * Inicializando ArrayList de fotos do pet recém criado caso seja nulo pra
	 * evitar NullPointerException
	 */
	public Pet save(Pet pet) {
		if (pet.getFotos() == null) {
			pet.setFotos(new ArrayList<Long>());
		}
		return repository.save(pet);
	}

	/*
	 * Aqui eu recebo um pet que foi modificado, e chamo da DB o Pet de mesmo ID
	 * Caso o usuário tenha sido modificado, retorno OperacaoNaoSuportadaException.
	 * Caso seja o mesmo usuário, aplico as alterações no pet da DB e retorno as
	 * novas informações
	 */
	public Pet alteraPet(Pet petModificado) {
		Pet pet = repository.findById(petModificado.getId())
				.orElseThrow(() -> new PetNaoEncontradoException("Pet não encontrado"));
		if (!petModificado.getUsuario().getId().equals(pet.getUsuario().getId())) {
			throw new OperacaoNaoSuportadaException("Este pet não é seu!");
		}
		petModificado.getLocalPet().setId(pet.getLocalPet().getId());
		BeanUtils.copyProperties(petModificado, pet);
//		pet.setNome(petModificado.getNome());
//		pet.setDescricao(petModificado.getDescricao());
//		pet.setEspecie(petModificado.getEspecie());
//		pet.setLocalPet(petModificado.getLocalPet());
//		pet.setObjetivo(petModificado.getObjetivo());
//		pet.setPorte(petModificado.getPorte());
//		pet.setSexo(petModificado.getSexo());
//		pet.setFotos(petModificado.getFotos());
		return repository.save(pet);
	}

	public Page<Pet> findAll(Example<Pet> pet, Pageable pageable) {
		return repository.findAll(pet, pageable);
	}

	public Pet findById(Long idPet) {
		return repository.findById(idPet).orElseThrow(() -> new PetNaoEncontradoException("Pet não encontrado"));
	}

	public void deleteById(Long idPet) {
		repository.delete(findById(idPet));
	}
}
