package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Address;


public interface AddressDAO {


	int create(Address address);
	Address findAddressById(long id);
	int findIdByAddress(Address address);
	Boolean findIfExists(Address address);

}
