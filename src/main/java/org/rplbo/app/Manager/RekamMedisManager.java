package org.rplbo.app.Manager;

import org.rplbo.app.DBConnectionManager;
import org.rplbo.app.Data.RekamMedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * ========================================================================
 *              PETUNJUK PENGERJAAN (BERLAKU UNTUK SETIAP METHOD)
 * ========================================================================
 *              TODO : LENGKAPILAH SETIAP METHOD YANG KOSONG
 * TODO 1: Siapkan Query SQL.
 * TODO 2: Buka koneksi database dan siapkan PreparedStatement. Gunakan prepareStatement(query)
 * TODO 3: Lakukan parameter binding (isi nilai tanda '?'). Gunakan setString() / setInt()
 * TODO 4: Eksekusi query ke dalam database. Gunakan executeQuery()
 * TODO 5: Evaluasi dan olah hasil eksekusi. Gunakan next()
 * TODO 6: Tangkap exception (SQLException) jika terjadi kegagalan database. (try catch (SQLException e))
 *
 * HINT ATURAN EMAS:
 * - Gunakan stmt.executeUpdate() jika merubah isi tabel (INSERT, UPDATE, DELETE).
 * - Gunakan stmt.executeQuery() jika hanya membaca tabel (SELECT).
 * - Untuk INSERT/UPDATE/DELETE : Cek apakah baris yang terpengaruh (rowsAffected) > 0, lalu return true/false.
 * - Untuk SELECT : Gunakan perulangan while(rs.next()) untuk mengekstrak data dan membungkusnya ke dalam List/Objek.
 *
 * Tampilkan pesan error di konsol (System.err.println) agar mudah di-debug,
 * lalu kembalikan nilai default (misal: return false, atau return List kosong).
 * ========================================================================
 */


public class RekamMedisManager {
    private Connection connection;

    public RekamMedisManager(Connection connection) {
        this.connection = connection;
    }

    // TODO LENGKAPILAH SETIAP METHOD YANG KOSONG DIBAWAH INI
    // --- 1. CREATE (Tambah Rekam Medis) ---
    public boolean tambahRekamMedis(String namaDokter, String namaPasien, String diagnosis, String tanggal) {
        String query = "INSERT INTO rekam_medis (nama_dokter, nama_pasien, diagnosis, tanggal) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, namaDokter);
            stmt.setString(2, namaPasien);
            stmt.setString(3, diagnosis);
            stmt.setString(4, tanggal);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    // --- 2. READ ALL ---
    public List<RekamMedis> getAllRekamMedis() {
        List<RekamMedis> list = new ArrayList<>();
        String query = "SELECT * FROM rekam_medis";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RekamMedis rm = new RekamMedis(
                        rs.getInt("id"),
                        rs.getString("nama_pasien"),
                        rs.getString("nama_dokter"),
                        rs.getString("diagnosis"),
                        rs.getString("tanggal")
                );
                list.add(rm);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return list;
    }

    // --- 3. UPDATE ---
    public boolean editRekamMedis(int idRekamMedis, String diagnosisBaru) {
        String query = "UPDATE rekam_medis SET diagnosis = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, diagnosisBaru);
            stmt.setInt(2, idRekamMedis);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    // --- 4. DELETE ---
    public boolean hapusRekamMedis(int idRekamMedis) {
        String query = "DELETE FROM rekam_medis WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idRekamMedis);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    // --- 5. READ ---
    public List<RekamMedis> cariRekamMedisPasien(String nama) {
        List<RekamMedis> list = new ArrayList<>();
        String query = "SELECT * FROM rekam_medis WHERE nama_pasien LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + nama + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RekamMedis rm = new RekamMedis(
                        rs.getInt("id"),
                        rs.getString("nama_pasien"),
                        rs.getString("nama_dokter"),
                        rs.getString("diagnosis"),
                        rs.getString("tanggal")
                );
                list.add(rm);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return list;
    }
}