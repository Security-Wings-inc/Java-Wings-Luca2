package ipInfo;

import BancoDeDados.BancoLooca;
import org.example.Console;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IPInfoDAOVm {

    public void cadastrarDados(IPInfo ipInfo, Console console){

        String sql = "INSERT INTO logEndereco (paisLog, regiaoLog, cidadeLog, ipLog, fkUsuarioLog) values (?, ?, ?, ?, ?)";


        try(PreparedStatement ps = BancoLooca.getbancoLooca().prepareStatement(sql)){
            ps.setString(1, ipInfo.getCountry());
            ps.setString(2, ipInfo.getRegion());

            ps.setString(3, ipInfo.getCity());
            ps.setString(4, ipInfo.getIpQuery());
            ps.setInt(5, console.getIdUser());
            ps.execute();
//            ps.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
