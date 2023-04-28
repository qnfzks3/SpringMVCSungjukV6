package qnfzks3.spring4.sungjukv6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import qnfzks3.spring4.sungjukv6.model.SungJukVO;
import qnfzks3.spring4.sungjukv6.service.SungJukV6Service;

import java.net.BindException;

/*작동 방식 - dao-로 데이터 가져와서 서비스에서 가져온 데이터를 가지고 작동 방식 함수들을 정의하고 그걸 컨트롤러로 가져와 변수에 담아 jsp(출력)로 보내준다.*/
 /*컨트롤러는 유저가 하는 행동에 대한 함수를 정의한 파일*/


/*controller는 작동 화면 연결*/

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
    //@ExceptionHandler(BindException.class) //예외처리  원래는 트라이 켓치에서 했지만 이렇게 빈으로 할수있다.
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




    // @GetMapping 과@PostMapping 에 대해서
    // 먼져 get 은 받아오는 역할을 보통 한다. vo에서도 get으로 받아와서 set으로 내보냈다. 여기서도 같이 get은 받아오는 역할을한다 -HTTP GET요청
    // post는 개시하다 출력을위해 보내는역할 - HTTP POST 요청 서버에 데이터를 전송하는데 사용
    //
    
    //성적 본문 조회 처리
    @GetMapping("/view")  //@GetMapping("링크지정")
    public ModelAndView view(@RequestParam int sjno){ //@RequestParam가 없어도 자동으로 써준다.- 자동으로 값을 가져와서 sjno에 넣어줌 int가아니라SungJukVO도 가능
        ModelAndView mv=new ModelAndView();
        String view = "sungjukfail";
        SungJukVO sj=sjsrv.readOneSungJuk(sjno);

        if (sj != null){
            mv.addObject("sj",sj);
            view = "sungjukview"; //이건 그냥 화면으로 이동

        }
        mv.setViewName(view);

        return mv;
    }

    @ExceptionHandler(BindException.class)
    public String typeMismatchParam(BindException ex){

        return "sungjukfail";
    }

    
    //성적 수정
    @GetMapping("/modify")
    public ModelAndView modify(@RequestParam int sjno){
        ModelAndView mv=new ModelAndView(); //1. 객체 만들고

        mv.addObject("sj",sjsrv.readOneSungJuk(sjno)); //2. sj 라는 이름으로 성적 데이터를 담는다. 이걸 mv라 정의하고
        mv.setViewName("sjmodify"); //3. 이 mv의 데이터 sj 를 데이터를 sjmodify 로 보냄
        return mv; //4. 보내는걸 리턴 


    }
    @PostMapping("/modify")  //포스트 처리를 하지 않으면 작동하지 않는다.  - 이제 입력완료 버튼을 누르면 반응 -
    public ModelAndView modifyok(SungJukVO sj){
        ModelAndView mv = new ModelAndView();  //보내기 위한 객체 생성
        String view = "sungjukfail";
        if(sjsrv.modifySungJuk(sj)) //boolean이니까 데이터가 있으면 true 없으면 false -> 위에 인풋테그에 값을 넣었다면 true 아니면 false
            view="redirect:/view?sjno="+sj.getSjno();  //뷰 페이지 --

        mv.setViewName(view); //view(위에 링크)로 보낸다.


        return mv;



    }





    //성적 삭제 -vo dao service controller jsp
    @GetMapping("/remove")  //<- 이렇게 링크로 /remove로 가면 바로 실행된다. 아래가
    public String remove(int sjno){
        sjsrv.removeSungJuk(sjno);
        return "redirect:/list";  //redirect:/list 하면 - 리스트 화면을 재호출한다  재호출 - redirect 
    }                              //redirect 로 하는건 우리가 해당 링크 번호(성적번호나,학번)로 클릭 했기 때문에 어떤 링크인지 모르니깐
                                  //다시 뒤로 돌아가게 하기위해 redirect로 넣어준다.
    
    
    //redirect = 클라이언트에게 /list를 서버에 요청하도록 지시      redirect-- 서버가 이쪽 주소로 다시 요청해 라는 의미(재요청)
    //forword =  클라이언트에게 해당 주소로 이동하게 함


}
