package com.tradestore.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tradestore.model.Trade;
import com.tradestore.service.TradeService;

/**
 * Class to handle rest api
 * 
 * @author SanketMalve
 *
 */
@RestController
public class TradeController {

	@Autowired
	TradeService tradeService;

	/**
	 * @param trade
	 * @return
	 */
	@PostMapping("/tradeStore")
	public ResponseEntity<String> saveTradeInStore(@RequestBody Trade trade) {
		if (tradeService.saveTrade(trade)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * Method to get a trade present
	 * 
	 * @return
	 */
	@GetMapping("/showTrade")
	public List<Trade> findAllTrades() {
		return tradeService.findAll();
	}
}
