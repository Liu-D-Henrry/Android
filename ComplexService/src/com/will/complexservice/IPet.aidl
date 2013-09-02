package com.will.complexservice;

import com.will.complexservice.Pet;
import com.will.complexservice.Person;

interface IPet {
	List<Pet> getPets(in Person owner);
}