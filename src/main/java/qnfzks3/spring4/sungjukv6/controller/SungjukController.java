package qnfzks3.spring4.sungjukv6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import qnfzks3.spring4.sungjukv6.model.SungJukVO;
import qnfzks3.spring4.sungjukv6.service.SungJukV6Service;




@Controller
public class SungjukController {
    private SungJukV6Service sjsrv;

    @Autowired   //@Autowired는 해당 클래스가 의존하고 있는 다른 객체를 찾아서 자동으로 주입
    public SungjukController(SungJukV6Service sjsrv) {
        this.sjsrv = sjsrv;
    }

    /*
    @RequestMapping(value = "/list",method = RequestMethod.GET) //@RequestMapping은 value로 url패턴을 지정하고 http에 get이나 post로 지정한다.-
    public String list(Model model){ //모델에 대한 생성자가 알아서 Model을 해줌-보이지 않을뿐 //@RequestMapping는  HTTP 요청을 처리하고, 결과를 응답합니다.

        System.out.println(sjsrv.readSungJuk());

        model.addAttribute("sjs",sjsrv.readSungJuk());//sungjuklist.jsp에 성적 조회결과 데이터를  sjs에 넣는다.
        return "sungjuklist";                  // 이 데이터를 sjs에 넣는 걸 sungjuklist 로 보낸다. - return으로 sungjuklist에서도 사용가능하게 함
                                                //추천 책 읽어봐도된다.
                                                */
    @GetMapping(value = "/list")            //이렇게 모델엔뷰 로 만들어서 간편하게 할 수 있다.
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView();

        //Sungjuklist.jsp에 성적 조회 결과를 sjs라는 이름으로 넘김
        mv.addObject("sjs",sjsrv.readSungJuk());
        mv.setViewName("sungjuklist");  //뷰 이름 지정
        return mv;





    }

    //성적 입력폼 표시
    @GetMapping("/add")
    public String add(){
        return "sungjuk";

    }
    
    //성적 입력 처리
    @PostMapping("/add")   //포스트
    public ModelAndView addok(SungJukVO sj){  //이렇게 SungJukVO sj 를 적어주면 값이 자동으로 불러와진다.
        ModelAndView mv=new ModelAndView();
        String view = "sungjukfail"; //sungjukfail 는 오류가 발생했을때 /sungjukfail로 홈페이지 이동

        //sjsrv.computeSungJuk(sj); //이것만 있을 때 서비스의 총첨 평균 등급계산한 결과를(computeSungJuk)   - sj에 넣어준다.
        if (sjsrv.newSungJuk(sj)){ //sungjukseviceImpl에서 불린newSungJuk 의 값이 true,false로 리턴함 - > 만약 트루면 if 아랫 값 실행
            mv.addObject("sj",sj);
            view="sungjukok"; //Sungjukv6dao에서 받아온 cnt가 -1 상태라서 오류가 나오기때문에 sungjukfail 화면으로 나온다.

        }

        //mv.addObject("sj",sj);
        mv.setViewName(view);               //불러와진 sj 를 그대로 mv에 "sj" 넣고 sungjukok에 보내준다.

        return mv;

    } //그냥 적어주면 한글은 깨져서 나오기때문에 web.xml에 따로 한글이 나오도록 지정을 해주어야한다.



}
