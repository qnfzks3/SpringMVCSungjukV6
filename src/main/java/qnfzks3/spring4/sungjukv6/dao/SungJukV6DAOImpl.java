package qnfzks3.spring4.sungjukv6.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import qnfzks3.spring4.sungjukv6.model.SungJukVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


//bean의 이름을 empdao로 지정,@Repository 어노테이션을 사용하여 Repository 클래스를 지정하면,
// 해당 클래스가 데이터 액세스 레이어에서 데이터베이스와 상호 작용하는 데 사용된다는걸 Spring에게 알려주는 것입니다. -DAO는 데이터베이스와 연결시켜주는 파일
@Repository("empdao")
public class SungJukV6DAOImpl implements SungJukV4DAO {

    private static final Logger logger = LogManager.getLogger(SungJukV6DAOImpl.class);
    //debug,info,warn,error,fatal       오류 메세지 개수 - 우린 info 아님 erorr로 오류메세지를 넣자

    private JdbcTemplate jdbcTemplate;
    //jdbc.properties 에 정의한 SQL 가져오기     - 변수에 어떤 값들이 들어갔는지 ,

    @Value("#{jdbc['insertSQL']}") private String insertSQL; //@Value("#{jdbc['insertSQL']}")는 SpEL 표현식을 사용하여
                                                            // insertSQL 변수에 jdbc 빈(Bean)의 insertSQL 프로퍼티 값을 동적으로 할당합니다.
    @Value("#{jdbc['selectSQL']}") private String selectSQL;
    @Value("#{jdbc['selectOneSQL']}") private String selectOneSQL;
    @Value("#{jdbc['updateSQL']}") private String updateSQL;
    @Value("#{jdbc['deleteSQL']}") private String deleteSQL;

    

    //1. 생성자를 만들어준다.
    @Autowired
    public SungJukV6DAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //jdbcTemplate Java에서 데이터베이스 연동을 쉽게 하기 위한 프레임워크
    //SQL 쿼리를 실행하고 결과를 처리하는 다양한 메서드를 제공  --SQL문으로 서버와 연결하기 위해 사용 @Autowired는
    // jdbcTemplate 이걸 가져와 쓸 때 마다 자동으로 연동되게 함



    
    
    @Override
    public int insertSungJuk(SungJukVO sj) {
        int cnt=-1;

        try{
            //매개변수 정의 -
            Object[] params = new Object[]{ //항상 객체로 만들어서 데이터에 넣어줘야한다. post로 이 객체에는 데이터가 들어가고 맴버변수로 맞게 데이터가 들어감
                    sj.getName(),sj.getKor(),sj.getEng(),sj.getMat(),sj.getTot(),sj.getAvg(),sj.getGrd()+""
            };
            cnt=jdbcTemplate.update(insertSQL,params); //sql문이 실행되어야 0보다 클수있음

        }catch (Exception ex){
            logger.error("insertSungJuk 오류!!");
            logger.info(ex.getMessage());
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
        return jdbcTemplate.query(selectSQL,mapper); //자바에서 while로 select를 컬렉션에 넣어줬지만
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
    public SungJukVO selectOneSungJuk(int sjno) { //학생번호를 이용해서 데이터 조회

        Object[] param=new Object[] {sjno};
        RowMapper<SungJukVO>mapper=new SungJukOneMapper(); //알트 엔터 메서드 생성

        SungJukVO sj = jdbcTemplate.queryForObject(selectOneSQL,mapper,param);


        return sj;
    }
    private class SungJukOneMapper implements RowMapper<SungJukVO> { //rowmapper 알트 엔터해서 생성자를 만들어준다.

        @Override
        public SungJukVO mapRow(ResultSet rs, int i) throws SQLException {
            SungJukVO sj=new SungJukVO(rs.getString(2),rs.getInt(3),rs.getInt(4),
                    rs.getInt(5),rs.getInt(6),rs.getDouble(7),rs.getString(8).charAt(0));
            sj.setSjno(rs.getInt(1));
            sj.setRegdate(rs.getString(9));

            return sj;
        }
    }



    @Override
    public int updateSungJuk(SungJukVO sj) { //이걸 어떻게 업데이트할거야? 그건 서비스로
        Object[] param = new Object[]{sj.getKor(),sj.getEng(),sj.getMat(),sj.getTot(),sj.getAvg(),sj.getGrd()+"",sj.getSjno()};

        return jdbcTemplate.update(updateSQL,param); //여기가 연결 되는 부분  jdbcTemplate 로 연결한다.
    }

    @Override
    public int deleteSungJuk(int sjno) {

        Object[] param = new Object[] {sjno}; //오브젝트 객체에 sjno를 찾아서
        
        return jdbcTemplate.update(deleteSQL,param); //sql 삭제하기
    }



}
