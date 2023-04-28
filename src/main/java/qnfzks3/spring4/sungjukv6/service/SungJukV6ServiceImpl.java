package qnfzks3.spring4.sungjukv6.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qnfzks3.spring4.sungjukv6.dao.SungJukV4DAO;
import qnfzks3.spring4.sungjukv6.dao.SungJukV6DAOImpl;
import qnfzks3.spring4.sungjukv6.model.SungJukVO;

import java.util.List;
import java.util.Scanner;


@Service("sjsrv")
public class SungJukV6ServiceImpl implements SungJukV6Service {


    private SungJukV4DAO sjdao = null;
    private static final Logger logger = LogManager.getLogger(SungJukV6DAOImpl.class);
    @Autowired
    public SungJukV6ServiceImpl(SungJukV4DAO sjdao) {

        this.sjdao = sjdao;
    }


    public boolean removeSungJuk(int sjno) {
        sjdao.deleteSungJuk(sjno);
        return true;
    }

    public boolean modifySungJuk(SungJukVO sj) {
        boolean result = false;
        computeSungJuk(sj);//총 학점 , 평균 ,등급 구하는 함수 이 파일  아래 있음

        
        if (sjdao.updateSungJuk(sj)>0) result = true;

        return result;
    }

    public SungJukVO readOneSungJuk(int sjno) {

        return sjdao.selectOneSungJuk(sjno);

    }

    // 성적 리스트 받아옴
    public List<SungJukVO> readSungJuk() {

        return sjdao.selectSungJuk();
    }

    // 성적 데이터 추가
    public boolean newSungJuk(SungJukVO sj) {
        boolean result=false;

        this.computeSungJuk(sj); //총점 평균 계산하고
        logger.info(sj);  //logger는  오류가 생겼을 때 (logger.~~) 이상의 오류를 출력한다. info error fatal 이런식으로 여태 우린 error만 봤기에
                            // info 이상의 오류를 출력한다.
        if (sjdao.insertSungJuk(sj)>0) //값을 넘겼으면 +1보다 크기에 성공하면 true 실패 false
            result=true;

        return result;

    }

    //성정 데이터 처리
    public void computeSungJuk(SungJukVO sj) {
        sj.setTot( sj.getKor() + sj.getEng() + sj.getMat() );
        sj.setAvg( (double) sj.getTot() / 3 );

        switch ((int)(sj.getAvg()/10)) {
            case 10: case 9: sj.setGrd('수'); break;
            case 8: sj.setGrd('우'); break;
            case 7: sj.setGrd('미'); break;
            case 6: sj.setGrd('양'); break;
            default: sj.setGrd('가'); break;
        }
    }

}
