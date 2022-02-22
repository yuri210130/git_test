// 서비스 등록 및 동의
	@ResponseBody
	@RequestMapping(value = FOLDER_PATH + "insertUser.do")
	public HashMap<String, Object> apiInsertUser(@ModelAttribute("searchVO") ApiVO searchVO, HttpServletRequest request, Model model, SessionStatus status) throws Exception {

		HashMap<String, Object> returnMap = new HashMap<>();
		
		// 서비스 등록
		HashMap<String, Object> insertMap = new HashMap<>();
		String url = "https://opm.kepco.co.kr/PrivateApi/insertUser.do";
		// insertUser parameter setting.
		ApiReqVO reqVO = new ApiReqVO();
		reqVO.setKepcoNum(searchVO.getCustNo());
		reqVO.setUserNm(searchVO.getCustNm());
		reqVO.setEntersApiTgtSysCd(searchVO.getTgtSysCd());
		reqVO.setKey("IVHG4o84obfo01EKwAcaT3Nbxu0ol1qnmU6M9gc7");
		
		String body = new Gson().toJson(reqVO);
		ApiResVO apiResVO = (ApiResVO) httpService.fromJson(url, body, ApiResVO.class);
		
		insertMap.put("statusCode", apiResVO.getStatusCode());
		if ("200".equals(apiResVO.getStatusCode())) {
			insertMap.put("seqUser", apiResVO.getSeqUser());
		}else {
			insertMap.put("message", apiResVO.getMessage());
			insertMap.put("timeStamp", apiResVO.getTimestamp());
		}
		
		// 서비스 동의
		HashMap<String, Object> agreeMap = new HashMap<>();
		String agreeUrl = "https://opm.kepco.co.kr/PrivateApi/agreeService.do";
		
		// agreeService parameter setting.
		reqVO.setSeqUser(apiResVO.getSeqUser());
		
		String agreeBody = new Gson().toJson(reqVO);
		ApiResVO apiRes2VO = (ApiResVO) httpService.fromJson(agreeUrl, agreeBody, ApiResVO.class);
		
		agreeMap.put("statusCode", apiRes2VO.getStatusCode());
		if ("200".equals(apiRes2VO.getStatusCode())) {
		}else {
			agreeMap.put("message", apiRes2VO.getMessage());
			agreeMap.put("timeStamp", apiRes2VO.getTimestamp());
		}
		
		returnMap.put("insertUser", insertMap);
		returnMap.put("agreeService", agreeMap);
		
		return returnMap;
	}
