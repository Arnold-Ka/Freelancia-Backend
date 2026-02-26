package com.hackers.freelancia.sercice;

import java.time.Instant;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.dto.CategoryDto;
import com.hackers.freelancia.dto.SkillsDto;
import com.hackers.freelancia.entity.Category;
import com.hackers.freelancia.entity.Skills;
import com.hackers.freelancia.mapper.Mapper;
import com.hackers.freelancia.repository.CategoryRepository;
import com.hackers.freelancia.repository.SkillsRepository;
import com.hackers.freelancia.service.FreelanciaService;

@ExtendWith(MockitoExtension.class)
public class SkillsUnitsTest {

    @Mock
    private SkillsRepository skillsRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private FreelanciaService service;

    @Mock
    private Mapper mapper;

    private Skills skills;
    private Skills skills2;
    private SkillsDto skillsDto;
    private SkillsDto skillsDto2;
    private CategoryDto categoryDto;
    private Category category;




    @BeforeEach
    public void setup(){
        categoryDto = new CategoryDto();
        categoryDto.setId("freeshbchdbbhdbhvdcia");
        categoryDto.setName("Design");
        categoryDto.setDescription("Design for the freelance");

        category = new Category();
        category.setId("freeshbchdbbhdbhvdcia");
        category.setName("Design");
        category.setDescription("Design for the freelance");
        category.setStatut(Statut.ACTIVE);
        category.setVersion(1L);
        category.setCreatedAt(Instant.now());
        category.setUpdatedAt(null);
        category.setCreatedBy(null);
        category.setLastModifiedBy(null);




        skillsDto = new SkillsDto();
        skillsDto.setId("freekdhhdfbbhdbhvdcia");
        skillsDto.setName("Figma");
        skillsDto.setDescription("Figma is one best tool for design");
        skillsDto.setCategoryId(categoryDto.getId());
        skillsDto.setIcon("figma.png");


        skills = new Skills();
        skills.setId("freekdhhdfbbhdbhvdcia");
        skills.setName("Figma");
        skills.setDescription("Figma is one best tool for design");
        skills.setCategory(category);
        skills.setIcon("figma.png");
        skills.setStatut(Statut.ACTIVE);
        skills.setVersion(1L);
        skills.setCreatedAt(Instant.now());
        skills.setUpdatedAt(null);
        skills.setCreatedBy(null);
        skills.setLastModifiedBy(null);


        skillsDto2 = new SkillsDto();
        skillsDto2.setId("freekdhhdserbhdbhvdcia");
        skillsDto2.setName("Photoshop");
        skillsDto2.setDescription("Photoshop is one best tool for design");
        skillsDto2.setIcon("photochop.png");
        skillsDto2.setCategoryId(categoryDto.getId());

        skills2 = new Skills();
        skills2.setId("freekdhhdserbhdbhvdcia");
        skills2.setName("Photoshop");
        skills2.setDescription("Photoshop is one best tool for design");
        skills2.setIcon("photochop.png");
        skills2.setCategory(category);
        skills2.setStatut(Statut.ACTIVE);
        skills2.setVersion(1L);
        skills2.setCreatedAt(Instant.now());
        skills2.setUpdatedAt(null);
        skills2.setCreatedBy(null);
        skills2.setLastModifiedBy(null);
    }



    @Test
    public void shouldReturnSkillsWhenFound() throws NotFoundException{
        Mockito.when(skillsRepository.findByIdAndStatut("freekdhhdfbbhdbhvdcia", Statut.ACTIVE)).thenReturn(Optional.of(skills));

        Mockito.when(mapper.maps(skills)).thenReturn(skillsDto);

        SkillsDto skillsFound = service.getSkill("freekdhhdfbbhdbhvdcia");
        
        
        Assertions.assertThat(skillsFound.getId()).isEqualTo(skills.getId());
        Assertions.assertThat(skillsFound.getName()).isEqualTo(skills.getName());
        Assertions.assertThat(skillsFound.getDescription()).isEqualTo(skills.getDescription());
        Assertions.assertThat(skillsFound.getIcon()).isEqualTo(skills.getIcon());
        Assertions.assertThat(skillsFound.getCategoryId()).isEqualTo(skills.getCategory().getId());
        
    }
}
