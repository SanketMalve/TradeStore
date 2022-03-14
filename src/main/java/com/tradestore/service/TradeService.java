package com.tradestore.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradestore.dao.TradeDao;
import com.tradestore.dao.TradeRepository;
import com.tradestore.exception.ResourceNotFoundException;
import com.tradestore.model.Trade;

/**
 * Service class for tradeStore
 * 
 * @author SanketMalve
 *
 */
@Service
public class TradeService {

	@Autowired
	TradeDao tradeDao;

	@Autowired
	TradeRepository tradeRepository;

	/**
	 * Method to store trade
	 * 
	 * @param trade
	 * @return
	 */
	public boolean saveTrade(Trade trade) {
		if (isVersionValid(trade)) {
			trade.setCreatedDate(LocalDate.now());
			tradeRepository.save(trade);
			return true;
		} else {
			throw new ResourceNotFoundException("  Trade is not valid " + trade.getTradeId());
		}
	}

	/**
	 * Check if trade already present in DB 
	 * if present check version of trade
	 * 
	 * @param trade
	 * @return
	 */
	public boolean isVersionValid(Trade trade) {
		if (validateMaturityDate(trade)) {
			Optional<Trade> exsitingTrade = tradeRepository.findById(trade.getTradeId());
			if (exsitingTrade.isPresent()) {
				return validateTradeVersion(trade, exsitingTrade.get());
			} else {
				return true;
			}
		}
		return false;
	}

	
	/**
	 * Method to check trade version
	 * if the lower version is being received by the store it will reject the trade
	 * throw an exception.
	 * 
	 * @param trade
	 * @param oldTrade
	 * @return
	 */
	private boolean validateTradeVersion(Trade trade, Trade oldTrade) {
		if (trade.getVersion() >= oldTrade.getVersion()) {
			return true;
		}
		return false;
	}

	
	/**
	 * Method to validate Maturity date of trade
	 * Trade Store should not allow the trade which has less maturity date then current date
	 * 
	 * @param trade
	 * @return
	 */
	private boolean validateMaturityDate(Trade trade) {
		return trade.getMaturityDate().isBefore(LocalDate.now()) ? false : true;
	}

	
	/**
	 * Method to update expire date with help of scheduler 
	 * 
	 */
	public void updateExpiryFlagOfTrade() {
		tradeRepository.findAll().stream().forEach(t -> {
			if (!validateMaturityDate(t)) {
				t.setExpiredFlag("Y");
				tradeRepository.save(t);
			}
		});
	}
	
	
	/**
	 * Method to Find all Trade present in database
	 * 
	 * @return
	 */
	public List<Trade> findAll() {
		return tradeRepository.findAll();
	}


}
