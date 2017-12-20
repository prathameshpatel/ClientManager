package io.github.prathameshpatel.clientmanager.dao;

import io.github.prathameshpatel.clientmanager.entity.Client;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

/**
 * Created by Prathamesh Patel on 12/15/2017.
 */

@Dao
public interface ClientDao {

    @Insert
    void insertClients(Client... clients);

    @Insert
    void insertClientList(List<Client> clientList);

    @Insert
    long insertClient(Client client);

    @Query("SELECT * FROM clients")
    List<Client> loadAllClients();

    @Query("SELECT client_id, first_name, last_name, is_favorite FROM clients")
    List<Client> loadFullNames();

    //client_id,first_name,last_name,address,phone,is_favorite
    @Query("SELECT * FROM clients Where client_id = :id")
    Client loadClientbyID(int id);

    @Query("DELETE FROM clients")
    void deleteAllClients();

    @Query("DELETE FROM clients WHERE client_id = :id")
    int deleteClient(int id);

    @Delete
    void deleteClients(Client... clients);

    @Query("DELETE FROM sqlite_sequence WHERE name = 'clients'")
    void deleteClientTable();


}
