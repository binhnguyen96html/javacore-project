package repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;


import repository.AssignmentBuildingRepository;
import repository.entity.AssignmentBuildingEntity;
import utils.ConnectionUtils;

public class AssignmentBuildingRepositoryImpl extends SimpleJdbcRepository<AssignmentBuildingEntity>
		implements AssignmentBuildingRepository {

	
	@Override
    public List<AssignmentBuildingEntity> findByBuildingId(Long buildingId) {
        String query = "select * from assignmentbuilding where buildingid = " + buildingId;
        return super.findByCondition(query);
    }

    @Override
    public AssignmentBuildingEntity findByStaffIdAndBuildingId(Long staffId, Long buildingId) {
        StringBuilder query = new StringBuilder()
                .append("SELECT * FROM assignmentbuilding")
                .append("\nWHERE staffid = " + staffId)
                .append("\nAND buildingid = " + buildingId);

        List<AssignmentBuildingEntity> result = super.findByCondition(query.toString());

        return (result != null & !result.isEmpty()) ? result.get(0) : null;
    }

    @Override
    public void assign(List<Long> deletedStaffIds, List<Long> addedStaffIds, Long buildingId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionUtils.getConnection();
            conn.setAutoCommit(false);

            if (CollectionUtils.isNotEmpty(deletedStaffIds)) {
                deleteByStaffIdIn(buildingId, deletedStaffIds);
            }
            if (CollectionUtils.isNotEmpty(addedStaffIds)) {
                List<AssignmentBuildingEntity> savedAssignment = addedStaffIds.stream()
                        .map(staffid -> new AssignmentBuildingEntity(buildingId, staffid))
                        .collect(Collectors.toList());

                saveAll(savedAssignment);
            }
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public void deleteByStaffIdIn(Long buildingId, List<Long> staffIds) {
        String tableName = getTableName();
        if (tableName == null) {
            return;
        }
        if (CollectionUtils.isEmpty(staffIds)) {
            return;
        }
        Connection conn;
        Statement stmt;
        try {
            conn = getConnection();
            StringBuilder query = new StringBuilder()
                    .append(" delete from ").append(tableName)
                    .append(" where buildingid = ").append(buildingId)
                    .append(" and staffid in (").append(staffIds.stream().map(Objects::toString).collect(Collectors.joining(","))).append(")");

            stmt = conn.createStatement();
            stmt.executeUpdate(query.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
