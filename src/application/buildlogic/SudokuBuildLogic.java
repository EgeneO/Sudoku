package application.buildlogic;

import java.io.IOException;

import application.computationlogic.GameLogic;
import application.persistence.LocalStorageImpl;
import application.problemdomain.IStorage;
import application.problemdomain.SudokuGame;
import application.userinterface.IUserInterfaceContract;
import application.userinterface.IUserInterfaceContract.View;
import application.userinterface.logic.ControlLogic;

public class SudokuBuildLogic {

	public static void build(View userInterface) throws IOException {
		SudokuGame initialState;
		IStorage storage = new LocalStorageImpl();
		
		try {
			initialState = storage.getGameData();
		} catch (IOException e) {
			initialState = GameLogic.getNewGame();
			storage.updateGameData(initialState);
		}
		
		IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage, userInterface);
		
		userInterface.setListener(uiLogic);
		userInterface.updateBoard(initialState);
	}

}
