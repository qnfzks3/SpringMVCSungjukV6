package qnfzks3.spring4.sungjukv6.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import qnfzks3.spring4.sungjukv6.model.SungJukVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("sjdao")
public class SungJukV6DAOImpl implements SungJukV4DAO {
    private JdbcTemplate jdbcTemplate;

    //jdbc.properties 에 정의한 SQL 가져오기     - 변수에 어떤 값들이 들어갔는지 ,
    @Value("#{jdbc['insertSQL']}") private String insertSQL;
    @Value("#{jdbc['selectSQL']}") private String selectSQL;
    @Value("#{jdbc['selectOneSQL']}") private String selectOneSQL;
    @Value("#{jdbc['updateSQL']}") private String updateSQL;
    @Value("#{jdbc['deleteSQL']}") private String deleteSQL;

    

    //1. 생성자를 만들어준다.
    @Autowired
    public SungJukV6DAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    
    
    @Override
    public int insertSungJuk(SungJukVO sj) {
        int cnt=-1;

        try{
            //매개변수 정의 -
            Object[] params = new Object[]{
                    sj.getName(),sj.getKor(),sj.getEng(),sj.getMat(),sj.getTot(),sj.getAvg(),sj.getGrd()
            };
            cnt=jdbcTemplate.update(insertSQL,params); //sql문이 실행되어야 0보다 클수있음

        }catch (Exception ex){
            System.out.println("insertSungJuk 오류!");
            ex.printStackTrace();
        }

        return cnt;
    }

    //2. SELECT부터  - 스프링이 연결하고 전부 다 해줄것임 - 컬렉션 만들고 jdbcTemplate 로 sql문 지정 끝
    // 연결도 트라이 켓치도 다 알아서 해줌
    @Override
    public List<SungJukVO> selectSungJuk() {
        //콜백 매서드 - SQL을 실행하고 ,결과집합이 존재하는 경우 
        // 스프링컨테이너에 의해 자동으로 호출하는 메서드  - 단, 반드시 mapRow를 정의해주어야한다.
        //실행 순서 - 
        RowMapper<SungJukVO>mapper=new SungJukMapper();  //3. 이 줄을 사용하고 매서드를 생성한다. 그 후에 메서드안에 알트 인설트해서 MAPROW를 정의
        return jdbcTemplate.query(selectSQL,mapper); //자바에서 while로 select 한걸 컬렉션에 넣어줬지만
                                                        // 여기선 그냥 셀렉트를 mapper에 넣어라 끝  -jdbcTemplate 에 모든 데이터가 들어있도록
    }
    private class SungJukMapper implements RowMapper<SungJukVO> {
        //메서드 구현 - 컬렉션
        @Override
        public SungJukVO mapRow(ResultSet rs, int num) throws SQLException { //mapper를 호출하면 mapRow에서 정의해주고 셀렉트 데이터를 넣어줌
            SungJukVO sj = new SungJukVO(rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5));
            sj.setSjno(rs.getInt(1));
            return sj;
        }
    }
    
    
    
    
    

    @Override
    public SungJukVO selectOneSungJuk(int sjno) {

         SungJukVO sj = null;


        return sj;
    }

    @Override
    public int updateSungJuk(SungJukVO sj) {

        int cnt = -1;



        return cnt;
    }

    @Override
    public int deleteSungJuk(int sjno) {

        int cnt = -1;



        return cnt;
    }

    
}
