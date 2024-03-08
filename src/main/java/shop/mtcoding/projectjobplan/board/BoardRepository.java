package shop.mtcoding.projectjobplan.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager entityManager;

    public BoardResponse.BoardDetailDTO detail(int idx) {
        String q = """
                select
                u.address, u.business_name, u.email, u.name, u.phone_number,
                b.id, b.title, b.content, b.field, b.position, b.salary, b.opening_date, b.closing_date, b.employer_id
                from user_tb u, board_tb b
                where b.id=? and b.employer_id = u.id
                """;
        Query query = entityManager.createNativeQuery(q);
        query.setParameter(1, idx);

        Object[] row = (Object[]) query.getSingleResult();

        String address = (String) row[0];
        String businessName = (String) row[1];
        String email = (String) row[2];
        String name = (String) row[3];
        String phoneNumber = (String) row[4];
        Integer id = (Integer) row[5];
        String title = (String) row[6];
        String content = (String) row[7];
        String field = (String) row[8];
        String position = (String) row[9];
        String salary = (String) row[10];
        Timestamp openingDate = (Timestamp) row[11];
        Timestamp closingDate = (Timestamp) row[12];
        Integer employerId = (Integer) row[13];

        BoardResponse.BoardDetailDTO boardDetailDTO = new BoardResponse.BoardDetailDTO();
        boardDetailDTO.setAddress(address);
        boardDetailDTO.setBusinessName(businessName);
        boardDetailDTO.setEmail(email);
        boardDetailDTO.setName(name);
        boardDetailDTO.setPhoneNumber(phoneNumber);
        boardDetailDTO.setId(id);
        boardDetailDTO.setTitle(title);
        boardDetailDTO.setContent(content);
        boardDetailDTO.setField(field);
        boardDetailDTO.setPosition(position);
        boardDetailDTO.setSalary(salary);
        boardDetailDTO.setOpeningDate(openingDate);
        boardDetailDTO.setClosingDate(closingDate);
        boardDetailDTO.setEmployerId(employerId);

        return boardDetailDTO;
    }



    public List<BoardResponse.BoardAndUserDTO> findByBoardtbAndUsertb(int page,String keyword) {

        final int COUNT = 10;
        int value = (page - 1) * COUNT;
        String q = """

                SELECT b.id, b.employer_id,b.title,b.content,b.field,
                                b.position,\s
                                b.salary,\s
                                b.opening_date,\s
                                b.closing_date,\s
                                b.created_at,\s
                                u.username,\s
                                u.address,\s
                                u.is_employer,\s
                                u.business_name,\s
                                s.skill_name\s
                            FROM\s
                                board_tb b\s
                            INNER JOIN\s
                                user_tb u\s
                            ON\s
                                b.employer_id = u.id\s
                            INNER JOIN\s
                                skill_tb s\s
                            ON\s
                                b.id = s.board_id\s
                            WHERE\s
                                u.is_employer = true\s
                                AND s.skill_name = ? ORDER BY b.id DESC LIMIT ?,?                        
                                """;
        Query query = entityManager.createNativeQuery(q);
        query.setParameter(1,keyword);
        query.setParameter(2, value);
        query.setParameter(3, COUNT);
        List<Object[]> results = query.getResultList();
        List<BoardResponse.BoardAndUserDTO> responseDTO = new ArrayList<>();

        for (Object[] result : results) {

            BoardResponse.BoardAndUserDTO dto = new BoardResponse.BoardAndUserDTO();
            dto.setId((Integer) result[0]);
            dto.setEmployerId((Integer) result[1]);
            dto.setTitle((String) result[2]);
            dto.setContent((String) result[3]);
            dto.setField((String) result[4]);
            dto.setPosition((String) result[5]);
            dto.setSalary((String) result[6]);
            dto.parseOpeningDate((Timestamp) result[7]);
            dto.parseClosingDate((Timestamp) result[8]);
            dto.parseCreatedAt((Timestamp) result[9]);
            dto.setUsername((String) result[10]);
            dto.setAddress((String) result[11]);
            dto.setEmployer((boolean) result[12]);
            dto.setBusinessName((String) result[13]);
            dto.setSkillName((String) result[14]);

            responseDTO.add(dto);
        }
        return responseDTO;
    }

    public List<BoardResponse.BoardAndUserDTO> findByBoardtbAndUsertb(int page) {


        final int COUNT = 10;
        int value = (page - 1) * COUNT;
        String q = """
                SELECT b.id, b.employer_id, b.title, b.content, b.field, b.position, b.salary, b.opening_date, b.closing_date, b.created_at, u.username, u.address, u.is_employer, u.business_name FROM board_tb b INNER JOIN user_tb u ON b.employer_id = u.id WHERE u.is_employer = true ORDER BY b.id DESC LIMIT ?,?;               
                        """;
        Query query = entityManager.createNativeQuery(q);
        query.setParameter(1, value);
        query.setParameter(2, COUNT);
        List<Object[]> results = query.getResultList();
        List<BoardResponse.BoardAndUserDTO> responseDTO = new ArrayList<>();

        for (Object[] result : results) {

            BoardResponse.BoardAndUserDTO dto = new BoardResponse.BoardAndUserDTO();
            dto.setId((Integer) result[0]);
            dto.setEmployerId((Integer) result[1]);
            dto.setTitle((String) result[2]);
            dto.setContent((String) result[3]);
            dto.setField((String) result[4]);
            dto.setPosition((String) result[5]);
            dto.setSalary((String) result[6]);
            dto.parseOpeningDate((Timestamp) result[7]);
            dto.parseClosingDate((Timestamp) result[8]);
            dto.parseCreatedAt((Timestamp) result[9]);
            dto.setUsername((String) result[10]);
            dto.setAddress((String) result[11]);
            dto.setEmployer((boolean) result[12]);
            dto.setBusinessName((String) result[13]);

            responseDTO.add(dto);
        }
        return responseDTO;
    }

    public List<BoardResponse.BoardAndUserDTO> findByBoardtbAndUsertb() {
        String q = """
                select b.id,b.employer_id, b.title, b.content,b.field,b.position,b.salary,b.opening_date,b.closing_date,b.created_at, u.username,u.address,u.is_employer,u.business_name from board_tb b inner join user_tb u on b.employer_id = u.id  order by id desc LIMIT 0,12;
                """;
        Query query = entityManager.createNativeQuery(q);
        List<Object[]> results = query.getResultList();
        List<BoardResponse.BoardAndUserDTO> responseDTO = new ArrayList<>();

        for (Object[] result : results) {

            BoardResponse.BoardAndUserDTO dto = new BoardResponse.BoardAndUserDTO();
            dto.setId((Integer) result[0]);
            dto.setEmployerId((Integer) result[1]);
            dto.setTitle((String) result[2]);
            dto.setContent((String) result[3]);
            dto.setField((String) result[4]);
            dto.setPosition((String) result[5]);
            dto.setSalary((String) result[6]);
            dto.parseOpeningDate((Timestamp) result[7]);
            dto.parseClosingDate((Timestamp) result[8]);
            dto.parseCreatedAt((Timestamp) result[9]);
            dto.setUsername((String) result[10]);
            dto.setAddress((String) result[11]);
            dto.setEmployer((boolean) result[12]);
            dto.setBusinessName((String) result[13]);


            responseDTO.add(dto);
        }
        return responseDTO;

    }


    @Transactional
    public Integer save(BoardRequest.SaveDTO requestDTO, Integer sessionUserId) {
        String q = """
                INSERT INTO board_tb
                (employer_id, title, content, field, position, salary, opening_date, closing_date, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, now())
                """;
        Query query = entityManager.createNativeQuery(q);
        query.setParameter(1, sessionUserId);
        query.setParameter(2, requestDTO.getTitle());
        query.setParameter(3, requestDTO.getContent());
        query.setParameter(4, requestDTO.getField());
        query.setParameter(5, requestDTO.getPosition());
        query.setParameter(6, requestDTO.getSalary());

        // String -> LocalData -> Timestamp
        LocalDate opening = LocalDate.parse(requestDTO.getOpeningDate());
        LocalDate closing = LocalDate.parse(requestDTO.getClosingDate());
        Timestamp openingDate = Timestamp.valueOf(opening.atStartOfDay());
        Timestamp closingDate = Timestamp.valueOf(closing.atStartOfDay());

        query.setParameter(7, openingDate);
        query.setParameter(8, closingDate);


        query.executeUpdate();

        String q1 = """
                select max(id) from board_tb
                """;
        Query query1 = entityManager.createNativeQuery(q1);
        Integer boardId = (Integer) query1.getSingleResult();
        return boardId ;
   }



    public List<Board> findAll() {
        String q = "select * from board_tb order by id desc";
        Query query = entityManager.createNativeQuery(q, Board.class);

        return (List<Board>) query.getResultList();

    }

    public Board findById(Integer id) {
        String q = "select * from board_tb where id = ?";
        Query query = entityManager.createNativeQuery(q, Board.class);
        query.setParameter(1, id);

        return (Board) query.getSingleResult();
    }
    public BoardResponse.ApplyFormDTO findWithBusinessNameById(Integer id) {
        String q = """
                SELECT u.id, u.business_name, b.title, b.closing_date
                FROM board_tb AS b
                JOIN user_tb AS u ON u.id = b.employer_id
                WHERE b.id = ?;
                """;
        Query query = entityManager.createNativeQuery(q);
        query.setParameter(1, id);

        Object[] row = (Object[]) query.getSingleResult();

        Integer employerId = (Integer) row[0];
        String businessName = (String) row[1];
        String title = (String) row[2];
        Timestamp closingDate = (Timestamp) row[3];

        BoardResponse.ApplyFormDTO responseDTO = new BoardResponse.ApplyFormDTO();
        responseDTO.setBusinessName(businessName);
        responseDTO.setEmployerId(employerId);
        responseDTO.setId(id);
        responseDTO.setTitle(title);
        responseDTO.setClosingDate(closingDate);

        return responseDTO;
    }

    public List<Board> findByEmployerId(Integer employerId) {
        String q = "select * from board_tb where employer_id = ? order by id desc";
        Query query = entityManager.createNativeQuery(q, Board.class);
        query.setParameter(1, employerId);

        return (List<Board>) query.getResultList();
    }

    @Transactional
    public Integer updateById(BoardRequest.UpdateDTO requestDTO, Integer id) {
        String q = """
                UPDATE board_tb
                SET
                title = ?,
                content = ?,
                field = ?,
                position = ?,
                salary = ?,
                opening_date = ?,
                closing_date = ?
                WHERE id = ?
                """;
        Query query = entityManager.createNativeQuery(q);
        query.setParameter(1, requestDTO.getTitle());
        query.setParameter(2, requestDTO.getContent());
        query.setParameter(3, requestDTO.getField());
        query.setParameter(4, requestDTO.getPosition());
        query.setParameter(5, requestDTO.getSalary());

        // String -> LocalData -> Timestamp
        LocalDate opening = LocalDate.parse(requestDTO.getOpeningDate());
        LocalDate closing = LocalDate.parse(requestDTO.getClosingDate());
        Timestamp openingDate = Timestamp.valueOf(opening.atStartOfDay());
        Timestamp closingDate = Timestamp.valueOf(closing.atStartOfDay());

        query.setParameter(6, openingDate);
        query.setParameter(7, closingDate);
        query.setParameter(8, id);

        return query.executeUpdate(); // 영향 받은 행
    }

    @Transactional
    public Integer deleteById(Integer id) {
        String q = "DELETE FROM board_tb WHERE id = ?";
        Query query = entityManager.createNativeQuery(q);
        query.setParameter(1, id);

        try {
            return query.executeUpdate(); // 영향 받은 행
        } catch (Exception e) {
            return null;
        }
    }


    public int countIsEmployerTrue() {
        String q = """
                SELECT COUNT(*) FROM board_tb b INNER JOIN user_tb u ON b.employer_id = u.id WHERE u.is_employer = true;
                """;
        Query query = entityManager.createNativeQuery(q);
        Long count = (Long) query.getSingleResult();

        return count.intValue();
    }
}
