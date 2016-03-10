package dashkevich.app.dao;

import dashkevich.app.model.Worker;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkerRowMapper implements RowMapper<Worker> {
    public Worker mapRow(ResultSet resultSet, int i) throws SQLException {
        Worker worker = new Worker();
        worker.setId(resultSet.getInt(1));
        worker.setDepartmentID(resultSet.getInt(2));
        worker.setName(resultSet.getString(3));
        worker.setBirthday(resultSet.getDate(4));
        worker.setSalary(resultSet.getInt(5));
        return worker;
    }
}