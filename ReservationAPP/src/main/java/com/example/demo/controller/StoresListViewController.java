package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.SearchCondition;
import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.FailedToGetSearchConditionsHistoryException;
import com.example.demo.factory.StringFormatUtil;
import com.example.demo.service.StoresListViewService;
import com.example.demo.session.UserSession;

/*
 * 店舗一覧画面のコントローラー
 * 予約の空き確認～予約確定までは、以下のコントローラーで行っている。
 * UserReservationController
 */

@Controller
@RequestMapping("/reservation/views")
public class StoresListViewController {
	
	private final StoresListViewService storesListViewService;
	private final UserSession userSession;

	public StoresListViewController(StoresListViewService storesListViewService,
			UserSession userSession) {
		this.storesListViewService = storesListViewService;
		this.userSession = userSession;
	}
	
	/*
	 * ユーザーログイン成功時に、店舗画面一覧が初期画面として表示される
	 * return 利用者情報(storeInfo)と取得した店舗一覧情報(storeViewList)をビューに送る
	 */
	
	@GetMapping("/user-login-complete")
	public String userLoginComplete(
				@ModelAttribute("userInfo") UserInfo userInfo,
				RedirectAttributes redirect
			) {
	
		userSession.setUserInfo(userInfo);
		redirect.addFlashAttribute(userInfo);
		return "redirect:/reservation/views/store-list";
	}
		
	/*
	 * 店舗一覧画面への遷移メソッド
	 * @param("storeViewList")	DBから取得した店舗データ10件分までのリストを格納
	 * @param("userInfo")		セッションで保持しているユーザー情報
	 * 
	 * @return				店舗一覧画面への遷移
	 */
	
	@GetMapping("/store-list")
	public String displayStoresList(
			Model model) {
		
		UserInfo userInfo = userSession.getUserInfo();
		
		List<SearchCondition> searchConditionsHistory = new ArrayList<>();
		try {
			searchConditionsHistory = storesListViewService.getSearchConditionsById(userInfo.getUserId());
			model.addAttribute("searchConditionsHistory",searchConditionsHistory);
			
		}catch(FailedToGetSearchConditionsHistoryException e) {
			e.printStackTrace();
		}
		List<StoreView> storeViewList = storesListViewService.getStoresList();
		
		userSession.setStoreViewList(storeViewList);
		
		model.addAttribute("searchCondition",new SearchCondition());
		model.addAttribute("storesViewList",storeViewList);
		model.addAttribute("userInfo",userSession.getUserInfo());
		return "view/stores-index";
	}
	
	/*
	 * 店舗情報を検索する時に使用するメソッド
	 * @param howToSearch 		検索方法を判定するパラメーター　0 :OR検索、1:AND検索 
	 * @param keywords 			検索に使用するキーワード
	 * @param cities			絞り込みで選択した都道府県のリスト
	 * @param dayOfWeeks 		絞り込みで選択した店舗が稼働している曜日
	 * 
	 * @param SearchCondition 	指定した検索条件と合致する店舗の判定メソッドを格納したクラス
	 * 
	 * @return 店舗一覧画面へ遷移する
	 */
	
	
	@GetMapping("/search-store-list")
	public String searchesStoresListView(
			@RequestParam(name="howToSearch",required=false) String howToSearch,
			@RequestParam(name="keywords",required=false) String keywords,
			@RequestParam(name="cities",required=false) List<String> cities,
			@RequestParam(name="dayOfWeeks",required=false) String[] dayOfWeeks,
			Model model) {
		
		UserInfo userInfo = userSession.getUserInfo();
		
		/*
		 * searchConditionsHistory		DBに保存してあった検索履歴をリストで格納する
		 */
		
		List<SearchCondition> searchConditionsHistory = storesListViewService.getSearchConditionsById(userInfo.getUserId());
		
		SearchCondition searchCondition = new SearchCondition(howToSearch,keywords,cities,dayOfWeeks);
		
		boolean successToSaveSearchCondition = false;
		
//		検索条件にキーワードが入力されていて、searchConditionsList内と検索条件が重複しなければ、
//		DBへ検索条件を保存する。履歴が無ければ、無条件で保存する。
		if(!(searchCondition.getKeywords() == null) && !(searchConditionsHistory == null)) {
			
			for(SearchCondition conditionInHistory:searchConditionsHistory) {
					if(conditionInHistory.equals(searchCondition)) { continue;}
				
				successToSaveSearchCondition = saveSearchConditionsAndVerify(searchCondition);
			}
			
		}else if(!(searchCondition.getKeywords() == null) && searchConditionsHistory == null) {
			successToSaveSearchCondition = saveSearchConditionsAndVerify(searchCondition);
			
			if(successToSaveSearchCondition) {
				searchConditionsHistory = new ArrayList<>();
				searchConditionsHistory.add(searchCondition);
			}
		}
		
		/*
		 * storeViewList 			店舗一覧画面に表示する店舗のリスト（命名は大分前につけたものなのでおかしいですが、余裕があれば修正します。）
		 */
		
		List<StoreView> storeViewList = extractSearchingStores(searchCondition);
		
//		検索条件をDBに保存できていれば、リストに格納する
		if(successToSaveSearchCondition && !(searchConditionsHistory == null)) { searchConditionsHistory.add(0,searchCondition);}
			
//		searchConditionsListの要素が5つを超えた場合、一番古い検索条件を削除する
//		戻り値は削除した要素の数が入る
		
		int deleteResult = 0;
		if(!(searchConditionsHistory == null)  && searchConditionsHistory.size() > 5) { 
			deleteResult = storesListViewService.removeOldestHistory(userInfo.getUserId());
		}
		
//		削除した要素が1個以上であれば、リストの最後の要素を削除
		if(deleteResult > 0) { searchConditionsHistory.remove(searchConditionsHistory.size()-1);}
				
		Map<String,String> errors = checkErrorMessages(searchCondition,storeViewList);
		
		model.addAttribute("searchConditionsHistory",searchConditionsHistory);
		model.addAttribute("searchCondition",searchCondition);
		model.addAttribute("errors",errors);
		model.addAttribute("storesViewList",storeViewList);
		model.addAttribute("userInfo",userInfo); 
		return "view/stores-index";
	}
	
	@PostMapping("/stores-list-matches-search-conditions")
	public String showStoresMatchingSearchConditions(
			@RequestParam(name="howToSearch",required=false) String howToSearch,
			@RequestParam(name="keywords",required=false) String keywords,
			@RequestParam(name="cities",required=false) String cities,
			@RequestParam(name="dayOfWeeks",required=false) String dayOfWeeks,
			@RequestParam(name="createdAt",required=false) String createdAt,
			@RequestParam(name="updatedAt",required=false) String updatedAt,
			Model model
			) {
		
		if(howToSearch != null)System.out.println("howToSearch:" + howToSearch);
		if(keywords != null)System.out.println("keywords:" + keywords);
		if(cities != null)System.out.println("city:"+ cities + " legth:" + cities.length());
		if(dayOfWeeks != null)System.out.println("dayOfWeeks:" + dayOfWeeks+ " legth:" + dayOfWeeks.length());
		if(createdAt != null)System.out.println("createdAt:" + createdAt);
		if(updatedAt != null)System.out.println("updatedAt:" + updatedAt);

		SearchCondition searchCondition = new SearchCondition();
		
		searchCondition.setHowToSearch(Integer.parseInt(howToSearch));
		if(keywords != null) searchCondition.setKeywords(keywords.substring(1,keywords.length()-1));
		if(cities != null) searchCondition.setCities(Arrays.asList(StringFormatUtil.StringToArrays(cities)));
		if(dayOfWeeks != null) searchCondition.setDayOfWeeksFromStrings(StringFormatUtil.StringToArrays(dayOfWeeks));
		if(createdAt != null) searchCondition.setCreatedAt(LocalDateTime.parse(createdAt));
		if(updatedAt != null) searchCondition.setCreatedAt(LocalDateTime.parse(updatedAt));
		
		System.out.println(searchCondition);
		
		UserInfo userInfo = userSession.getUserInfo();
		
		List<StoreView> storeViewList = extractSearchingStores(searchCondition);

		Map<String,String> errors = checkErrorMessages(searchCondition,storeViewList);
		
//		model.addAttribute("searchConditionsHistory",searchConditionsHistory);
		model.addAttribute("searchCondition",searchCondition);
		model.addAttribute("errors",errors);
		model.addAttribute("storesViewList",storeViewList);
		model.addAttribute("userInfo",userInfo); 
		return "view/stores-index";
	}

	/*
	 * UserReservationControllerへ遷移する時のメソッド
	 * @param storeView 		一覧から選択した店舗の情報が格納されているエンティティクラス
	 * @param userSession 		利用者情報を保持するセッションクラス
	 * 
	 * @return UserReservationControllerへ遷移する
	 */
	
	@PostMapping("/to-reservation-controller")
	public String moveToReservationController(		
			@ModelAttribute StoreView storeView,
			RedirectAttributes redirect) {
		
		redirect.addFlashAttribute("storeView",storeView);
		redirect.addFlashAttribute("userSession",userSession);
		return "redirect:/reservation/reserve/store-available-days";
	}	
	
	/*
	 * UserReservationControllerからUserInfoを受け取り
	 * 店舗情報一覧画面に戻すメソッド
	 */
	
	@GetMapping("/open-store-list")
	public String displayStoresListFromUserReservationController(
			@ModelAttribute("userInfo") UserInfo userInfo,
			RedirectAttributes redirect
			) {
		
		userSession.setUserInfo(userInfo);
		return "redirect:/reservation/views/store-list";
	}
	
	@GetMapping("/user-mypage")
	public String toUserMyPage(
			RedirectAttributes redirect) {
		
		redirect.addFlashAttribute("userSession",userSession);
		return "redirect:/reservation/reserve/user-mypage";
	}
	
	/*
	 * 検索条件に合致する店舗情報を店舗情報の中から抽出してリストで戻すメソッド
	 * 
	 */
	
	Map<String,String> checkErrorMessages(SearchCondition searchCondition,List<StoreView> storeViewList){
	
		Map<String,String> errors = new HashMap<>();
		if(searchCondition.getKeywords().isEmpty()) {
			errors.put("keywordError", "キーワードを入力してください。");
		}
		if(storeViewList.isEmpty()) {
			errors.put("NotFoundStoresError","キーワードが未入力か店舗が見つかりませんでした。");
		}
		
		return errors;
	}
	
	List<StoreView> extractSearchingStores(SearchCondition searchCondition){
		List<StoreView> listOfApplicableStores = new ArrayList<>(); 
		
		for(StoreView storeView:userSession.getStoreViewList()) {
			
			if(searchCondition.checkAllSearchCriteria(storeView)) {
				listOfApplicableStores.add(storeView);
				continue;
			}
		}
		return listOfApplicableStores;
	}
	
	boolean saveSearchConditionsAndVerify(SearchCondition searchCondition) {
		if(searchCondition.getKeywords() == null) return false;
		
		UserInfo userInfo = userSession.getUserInfo();
		try {
			return storesListViewService.saveSearchConditions(userInfo.getUserId(), searchCondition);
			
		}catch(FailedToGetSearchConditionsHistoryException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
