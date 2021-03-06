package br.com.academiadev.BatataComBaconSpring.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import br.com.academiadev.BatataComBaconSpring.dto.post.PostPetDTO;
import br.com.academiadev.BatataComBaconSpring.dto.request.ResponsePetDTO;
import br.com.academiadev.BatataComBaconSpring.model.Pet;

@Mapper(componentModel = "spring")
public interface PetMapper {

	//O DTO não carrega estas informações, então informo para ignorá-las
	@Mappings({//
		@Mapping(target = "id", ignore = true), //
		@Mapping(target = "createdAt", ignore = true), //
		@Mapping(target = "updatedAt", ignore = true), //
		@Mapping(target = "usuario", ignore = true), //
	})
	Pet toPet(PostPetDTO dto);
	
	/*
	 * idUsuario é de uma nested entity, então preciso especificar aqui,
	 * além disso, quero mostrar a data segundo a forma dd/MM/yyyy HH:mm
	 */
	@Mappings({//
	@Mapping(target = "idUsuario",source = "usuario.id"), //
	@Mapping(target = "created_at",source = "createdAt", dateFormat = "dd/MM/yyyy HH:mm") //
})
	ResponsePetDTO toDTO(Pet pet);
	
	List<Pet> toPet(List<PostPetDTO> dtos);
	
	List<ResponsePetDTO> toDTO(List<Pet> pets);

}
