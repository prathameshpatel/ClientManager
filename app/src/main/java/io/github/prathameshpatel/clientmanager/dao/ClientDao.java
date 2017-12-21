package io.github.prathameshpatel.clientmanager.dao;

import io.github.prathameshpatel.clientmanager.entity.Client;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("SELECT client_id, first_name, last_name, is_favorite FROM clients ORDER BY first_name ASC")
    List<Client> loadFullNames();

    @Query("SELECT client_id, first_name, last_name, is_favorite FROM clients WHERE is_favorite = :isFav ORDER BY first_name ASC")
    List<Client> loadFavoriteFullNames(int isFav);

    //client_id,first_name,last_name,address,phone,is_favorite
    @Query("SELECT * FROM clients Where client_id = :id")
    Client loadClientbyID(int id);

    @Query("UPDATE clients SET is_favorite = :one WHERE client_id = :clientid")
    void addFavorite(int clientid, int one);

    @Query("UPDATE clients SET is_favorite = :zero WHERE client_id = :clientid")
    void removeFavorite(int clientid, int zero);

    @Update
    int updateClient(Client client);

    @Query("DELETE FROM clients")
    void deleteAllClients();

    @Query("DELETE FROM clients WHERE client_id = :id")
    int deleteClient(int id);

    @Delete
    void deleteClients(Client... clients);

    @Query("DELETE FROM sqlite_sequence WHERE name = 'clients'")
    void deleteClientTable();


}
