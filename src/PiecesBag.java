import java.util.*;
public class PiecesBag {
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public ArrayList<Piece> pieces;
	public ArrayList<Piece> secondaryPieces;
	public ArrayList<Piece> specialPieces;
	public Piece edgePiece1;
	public Piece edgePiece2;
	public ArrayList<Piece> tertiaryPieces;
	public PiecesBag() {
		pieces = new ArrayList<>();
		secondaryPieces = new ArrayList<>();
		specialPieces = new ArrayList<>();
		tertiaryPieces = new ArrayList<>();
		
		// X
		// XX
		
		edgePiece1 = new Piece(1, 1);
		edgePiece1.name = "edgePiece1";
		edgePiece1.rowOffset = 0;
		edgePiece1.cells[0][0].filled = true;
		edgePiece1.cells[0][0].connected[UP] = true;
		edgePiece1.addVert(edgePiece1.cells[0][0]);
		edgePiece1.addHoriz(edgePiece1.cells[0][0]);
		
		edgePiece2 = new Piece(1, 1);
		edgePiece2.name = "edgePiece2";
		edgePiece2.rowOffset = 0;
		edgePiece2.cells[0][0].filled = true;
		edgePiece2.cells[0][0].connected[DOWN] = true;
		edgePiece2.addVert(edgePiece2.cells[0][0]);
		edgePiece2.addHoriz(edgePiece2.cells[0][0]);

		// X
		// XX
		Piece L1 = new Piece(2, 2);
		L1.rowOffset = 1;
		L1.name = "L1";
		L1.cells[0][0].filled = true;
		L1.cells[0][0].connected[DOWN] = true;
		L1.cells[1][0].filled = true;
		L1.cells[1][0].connected[RIGHT] = true;
		L1.cells[1][0].connected[UP] = true;
		L1.cells[1][1].filled = true;
		L1.cells[1][1].connected[LEFT] = true;
		L1.addVert(L1.cells[0][0]);
		L1.addHoriz(L1.cells[1][1]);
		pieces.add(L1);
		
		Piece L2 = new Piece(L1);
		L2.rowOffset = 0;
		L2.name = "L2";
		L2.addVert(L2.cells[0][0]);
		L2.addHoriz(L2.cells[1][1]);
		pieces.add(L2);
		
		// XX
		// X
		Piece L3 = new Piece(2, 2);
		L3.rowOffset = 0;
		L3.name = "L3";
		L3.cells[0][0].filled = true;
		L3.cells[0][0].connected[DOWN] = true;
		L3.cells[0][0].connected[RIGHT] = true;
		L3.cells[0][1].filled = true;
		L3.cells[0][1].connected[LEFT] = true;
		L3.cells[1][0].filled = true;
		L3.cells[1][0].connected[UP] = true;
		L3.addVert(L3.cells[1][0]);
		L3.addHoriz(L3.cells[0][1]);
		pieces.add(L3);
		
		Piece L4 = new Piece(L3);
		L4.rowOffset = 1;
		L4.name = "L4";
		L4.addVert(L4.cells[1][0]);
		L4.addHoriz(L4.cells[0][1]);
		pieces.add(L4);
		
		// XX
		//  X
		Piece L5 = new Piece(2, 2);
		L5.name = "L5";
		L5.rowOffset = 0;
		L5.cells[0][0].filled = true;
		L5.cells[0][0].connected[RIGHT] = true;
		L5.cells[0][1].filled = true;
		L5.cells[0][1].connected[LEFT] = true;
		L5.cells[0][1].connected[DOWN] = true;
		L5.cells[1][1].filled = true;
		L5.cells[1][1].connected[UP] = true;
		L5.addVert(L5.cells[1][1]);
		L5.addHoriz(L5.cells[0][0]);
		secondaryPieces.add(L5);
		
		//  X
		// XX
		Piece L6 = new Piece(2, 2);
		L6.name = "L6";
		L6.rowOffset = 1;
		L6.cells[0][1].filled = true;
		L6.cells[0][1].connected[DOWN] = true;
		L6.cells[1][1].filled = true;
		L6.cells[1][1].connected[LEFT] = true;
		L6.cells[1][1].connected[UP] = true;
		L6.cells[1][0].filled = true;
		L6.cells[1][0].connected[RIGHT] = true;
		L6.addVert(L6.cells[0][1]);
		L6.addHoriz(L6.cells[1][0]);
		secondaryPieces.add(L6);
		
		// XX
		Piece L7 = new Piece(1, 2);
		L7.name = "L7";
		L7.rowOffset = 0;
		L7.cells[0][0].filled = true;
		L7.cells[0][0].connected[RIGHT] = true;
		L7.cells[0][1].filled = true;
		L7.cells[0][1].connected[LEFT] = true;
		L7.addVert(L7.cells[0][1]);
		L7.addVert(L7.cells[0][0]);
		L7.addHoriz(L7.cells[0][0]);
		L7.addHoriz(L7.cells[0][1]);
		specialPieces.add(L7);
		
		// X
		// X
		Piece L8 = new Piece(2, 1);
		L8.name = "L8";
		L8.rowOffset = 0;
		L8.cells[0][0].filled = true;
		L8.cells[0][0].connected[DOWN] = true;
		L8.cells[1][0].filled = true;
		L8.cells[1][0].connected[UP] = true;
		L8.addVert(L8.cells[0][0]);
		L8.addVert(L8.cells[1][0]);
		L8.addHoriz(L8.cells[0][0]);
		L8.addHoriz(L8.cells[1][0]);
		pieces.add(L8);
		
		// X
		// X
		// XX
		Piece LL1 = new Piece(3, 2);
		LL1.name = "LL1";
		LL1.rowOffset = 0;
		LL1.cells[0][0].filled = true;
		LL1.cells[0][0].connected[DOWN] = true;
		LL1.cells[1][0].filled = true;
		LL1.cells[1][0].connected[UP] = true;
		LL1.cells[1][0].connected[DOWN] = true;
		LL1.cells[2][0].filled = true;
		LL1.cells[2][0].connected[UP] = true;
		LL1.cells[2][0].connected[RIGHT] = true;
		LL1.cells[2][1].filled = true;
		LL1.cells[2][1].connected[LEFT] = true; 
		LL1.addVert(LL1.cells[0][0]);
		LL1.addHoriz(LL1.cells[2][1]);
		tertiaryPieces.add(LL1);
		
		// X
		// X
		// XX
		Piece LL2 = new Piece(LL1);
		LL2.name = "LL2";
		LL2.rowOffset = 1;
		tertiaryPieces.add(LL2);
		
		// X
		// X
		// XX
		Piece LL3 = new Piece(LL1);
		LL3.name = "LL3";
		LL3.rowOffset = 2;
		tertiaryPieces.add(LL3);
		
		//   X
		// XXX
		Piece LL4 = new Piece(2, 3);
		LL4.name = "LL4";
		LL4.rowOffset = 1;
		LL4.cells[0][2].filled = true;
		LL4.cells[0][2].connected[DOWN] = true;
		LL4.cells[1][0].filled = true;
		LL4.cells[1][0].connected[RIGHT] = true;
		LL4.cells[1][1].filled = true;
		LL4.cells[1][1].connected[LEFT] = true;
		LL4.cells[1][1].connected[RIGHT] = true;
		LL4.cells[1][2].filled = true;
		LL4.cells[1][2].connected[UP] = true;
		LL4.cells[1][2].connected[LEFT] = true;
		LL4.addVert(LL4.cells[0][2]);
		LL4.addHoriz(LL4.cells[1][0]);
		tertiaryPieces.add(LL4);
		
		// XXX
		//   X	
		Piece LL5 = new Piece(2, 3);
		LL5.name = "LL5";
		LL5.rowOffset = 0;
		LL5.cells[0][0].filled = true;
		LL5.cells[0][0].connected[RIGHT] = true;
		LL5.cells[0][1].filled = true;
		LL5.cells[0][1].connected[LEFT] = true;
		LL5.cells[0][1].connected[RIGHT] = true;
		LL5.cells[0][2].filled = true;
		LL5.cells[0][2].connected[LEFT] = true;
		LL5.cells[0][2].connected[DOWN] = true;
		LL5.cells[1][2].filled = true;
		LL5.cells[1][2].connected[UP] = true;	
		LL5.addVert(LL5.cells[1][2]);
		LL5.addHoriz(LL5.cells[0][0]);
		tertiaryPieces.add(LL5);
		
		// XX
		//  X
		// 	X
		Piece LL6 = new Piece(3, 2);
		LL6.name = "LL6";
		LL6.rowOffset = 0;
		LL6.cells[0][0].filled = true;
		LL6.cells[0][0].connected[RIGHT] = true;
		LL6.cells[0][1].filled = true;
		LL6.cells[0][1].connected[LEFT] = true;
		LL6.cells[0][1].connected[DOWN] = true;
		LL6.cells[1][1].filled = true;
		LL6.cells[1][1].connected[UP] = true;
		LL6.cells[1][1].connected[DOWN] = true;
		LL6.cells[2][1].filled = true;
		LL6.cells[2][1].connected[UP] = true;
		LL6.addVert(LL6.cells[2][1]);
		LL6.addHoriz(LL6.cells[0][0]);
		tertiaryPieces.add(L6);
		
		//  X
		//  X
		// XX
		Piece LL7 = new Piece(3, 2);
		LL7.name = "LL7";
		LL7.rowOffset = 2;
		LL7.cells[0][1].filled = true;
		LL7.cells[0][1].connected[DOWN] = true;
		LL7.cells[1][1].filled = true;
		LL7.cells[1][1].connected[UP] = true;
		LL7.cells[1][1].connected[DOWN] = true;
		LL7.cells[2][1].filled = true;
		LL7.cells[2][1].connected[UP] = true;
		LL7.cells[2][1].connected[LEFT] = true;
		LL7.cells[2][0].filled = true;
		LL7.cells[2][0].connected[RIGHT] = true;
		LL7.addVert(LL7.cells[0][1]);
		LL7.addHoriz(LL7.cells[2][0]);
		tertiaryPieces.add(LL7);
		
		// XX
		// X
		// X
		Piece LL8 = new Piece(3, 2);
		LL8.name = "LL8";
		LL8.rowOffset = 0;
		LL8.cells[0][0].filled = true;
		LL8.cells[0][0].connected[DOWN] = true;
		LL8.cells[0][0].connected[RIGHT] = true;
		LL8.cells[0][1].filled = true;
		LL8.cells[0][1].connected[LEFT] = true;
		LL8.cells[1][0].filled = true;
		LL8.cells[1][0].connected[UP] = true;
		LL8.cells[1][0].connected[DOWN] = true;
		LL8.cells[2][0].filled = true;
		LL8.cells[2][0].connected[UP] = true; 
		LL8.addVert(LL8.cells[2][0]);
		LL8.addHoriz(LL8.cells[0][1]);
		tertiaryPieces.add(LL8);
		
		// XX
		// X
		// X
		Piece LL9 = new Piece(LL8);
		LL9.name = "LL9";
		LL9.rowOffset = 1;
		tertiaryPieces.add(LL9);
		
		// XX
		// X
		// X
		Piece LL10 = new Piece(LL8);
		LL10.name = "LL10";
		LL10.rowOffset = 2;
		tertiaryPieces.add(LL10);
		
		// X
		// XXX
		Piece LL11 = new Piece(2, 3);
		LL11.name = "LL11";
		LL11.rowOffset = 0;
		LL11.cells[0][0].filled = true;
		LL11.cells[0][0].connected[DOWN] = true;
		LL11.cells[1][0].filled = true;
		LL11.cells[1][0].connected[UP] = true;
		LL11.cells[1][0].connected[RIGHT] = true;
		LL11.cells[1][1].filled = true;
		LL11.cells[1][1].connected[LEFT] = true;
		LL11.cells[1][1].connected[RIGHT] = true;
		LL11.cells[1][2].filled = true;
		LL11.cells[1][2].connected[LEFT] = true;
		LL11.addVert(LL11.cells[0][0]);
		LL11.addHoriz(LL11.cells[1][2]);
		tertiaryPieces.add(LL11);
		
		// XXX
		// X
		Piece LL12 = new Piece(2, 3);
		LL12.name = "LL12";
		LL12.rowOffset = 0;
		LL12.cells[1][0].filled = true;
		LL12.cells[1][0].connected[UP] = true;
		LL12.cells[0][0].filled = true;
		LL12.cells[0][0].connected[DOWN] = true;
		LL12.cells[0][0].connected[RIGHT] = true;
		LL12.cells[0][1].filled = true;
		LL12.cells[0][1].connected[LEFT] = true;
		LL12.cells[0][1].connected[RIGHT] = true;
		LL12.cells[0][2].filled = true;
		LL12.cells[0][2].connected[LEFT] = true;
		LL12.addVert(LL12.cells[1][0]);
		LL12.addHoriz(LL12.cells[0][2]);
		tertiaryPieces.add(LL12);
		
		// X
		// XXX
		Piece LL13 = new Piece(LL11);
		LL13.name = "LL13";
		LL13.rowOffset = 0;
		tertiaryPieces.add(LL13);
		
		Piece LL14 = new Piece(LL12);
		LL14.name = "LL14";
		LL14.rowOffset = 0;
		tertiaryPieces.add(LL14);
		
		// X
		// XXX
		// X
		Piece T1 = new Piece(3, 3);
		T1.name = "T1";
		T1.rowOffset = 0;
		T1.cells[0][0].filled = true;
		T1.cells[0][0].connected[DOWN] = true;
		T1.cells[1][0].filled = true;
		T1.cells[1][0].connected[UP] = true;
		T1.cells[1][0].connected[DOWN] = true;
		T1.cells[1][0].connected[RIGHT] = true;
		T1.cells[2][0].filled = true;
		T1.cells[2][0].connected[UP] = true;
		T1.cells[1][1].filled = true;
		T1.cells[1][1].connected[RIGHT] = true;
		T1.cells[1][1].connected[LEFT] = true;
		T1.cells[1][2].filled = true;
		T1.cells[1][2].connected[LEFT] = true;
		T1.addVert(T1.cells[0][0]);
		T1.addVert(T1.cells[2][0]);
		T1.addHoriz(T1.cells[1][2]);
		tertiaryPieces.add(T1);

		// X
		// XXX
		// X
		Piece T2 = new Piece(T1);
		T2.name = "T2";
		T2.rowOffset = 1;
		tertiaryPieces.add(T2);
		
		// X
		// XXX
		// X
		Piece T3 = new Piece(T1);
		T3.name = "T3";
		T3.rowOffset = 2;
		tertiaryPieces.add(T3);
		
		// XXX
		//  X
		//  X
		Piece T4 = new Piece(3, 3);
		T4.name = "T4";
		T4.rowOffset = 0;
		T4.cells[0][0].filled = true;
		T4.cells[0][0].connected[RIGHT] = true;
		T4.cells[0][1].filled = true;
		T4.cells[0][1].connected[LEFT] = true;
		T4.cells[0][1].connected[RIGHT] = true;
		T4.cells[0][1].connected[DOWN] = true;
		T4.cells[0][2].filled = true;
		T4.cells[0][2].connected[LEFT] = true;
		T4.cells[1][1].filled = true;
		T4.cells[1][1].connected[UP] = true;
		T4.cells[1][1].connected[DOWN] = true;
		T4.cells[2][1].filled = true;
		T4.cells[2][1].connected[UP] = true;
		T4.addVert(T4.cells[2][1]);
		T4.addHoriz(T4.cells[0][0]);
		T4.addHoriz(T4.cells[0][2]);
		tertiaryPieces.add(T4);
		
		//  X
		//  X
		// XXX
		Piece T5 = new Piece(3, 3);
		T5.name = "T5";
		T5.rowOffset = 2;
		T5.cells[2][0].filled = true;
		T5.cells[2][0].connected[RIGHT] = true;
		T5.cells[2][1].filled = true;
		T5.cells[2][1].connected[LEFT] = true;
		T5.cells[2][1].connected[RIGHT] = true;
		T5.cells[2][1].connected[UP] = true;
		T5.cells[2][2].filled = true;
		T5.cells[2][2].connected[LEFT] = true;
		T5.cells[1][1].filled = true;
		T5.cells[1][1].connected[UP] = true;
		T5.cells[1][1].connected[DOWN] = true;
		T5.cells[0][1].filled = true;
		T5.cells[0][1].connected[DOWN] = true;
		T5.addVert(T5.cells[1][0]);
		T5.addHoriz(T5.cells[2][0]);
		T5.addHoriz(T5.cells[2][2]);
		tertiaryPieces.add(T5);
		
		//   X
		// XXX
		//   X
		Piece T6 = new Piece(3, 3);
		T6.name = "T6";
		T6.rowOffset = 1;
		T6.cells[0][2].filled = true;
		T6.cells[0][2].connected[DOWN] = true;
		T6.cells[1][2].filled = true;
		T6.cells[1][2].connected[LEFT] = true;
		T6.cells[1][2].connected[UP] = true;
		T6.cells[1][2].connected[DOWN] = true;
		T6.cells[2][2].filled = true;
		T6.cells[2][2].connected[UP] = true;
		T6.cells[1][1].filled = true;
		T6.cells[1][1].connected[LEFT] = true;
		T6.cells[1][1].connected[RIGHT] = true;
		T6.cells[1][0].filled = true;
		T6.cells[1][0].connected[RIGHT] = true;
		T6.addVert(T6.cells[0][2]);
		T6.addVert(T6.cells[2][2]);
		T6.addHoriz(T6.cells[1][0]);
		tertiaryPieces.add(T6);
		
		// X
		// XX
		// X
		Piece ST1 = new Piece(3, 2);
		ST1.name = "ST1";
		ST1.rowOffset = 0;
		ST1.cells[0][0].filled = true;
		ST1.cells[0][0].connected[DOWN] = true;
		ST1.cells[1][0].filled = true;
		ST1.cells[1][0].connected[UP] = true;
		ST1.cells[1][0].connected[DOWN] = true;
		ST1.cells[1][0].connected[RIGHT] = true;
		ST1.cells[2][0].filled = true;
		ST1.cells[2][0].connected[UP] = true;
		ST1.cells[1][1].filled = true;
		ST1.cells[1][1].connected[RIGHT] = true;
		ST1.cells[1][1].connected[LEFT] = true;
		ST1.addVert(ST1.cells[0][0]);
		ST1.addVert(ST1.cells[2][0]);
		ST1.addHoriz(ST1.cells[1][1]);
		tertiaryPieces.add(ST1);

		// X
		// XX
		// X
		Piece ST2 = new Piece(ST1);
		ST2.name = "ST2";
		ST2.rowOffset = 1;
		tertiaryPieces.add(ST2);
		
		// X
		// XXX
		// X
		Piece ST3 = new Piece(ST1);
		ST3.name = "ST3";
		ST3.rowOffset = 2;
		tertiaryPieces.add(ST3);
		
		// XXX
		//  X
		Piece ST4 = new Piece(2, 3);
		ST4.name = "ST4";
		ST4.rowOffset = 0;
		ST4.cells[0][0].filled = true;
		ST4.cells[0][0].connected[RIGHT] = true;
		ST4.cells[0][1].filled = true;
		ST4.cells[0][1].connected[LEFT] = true;
		ST4.cells[0][1].connected[RIGHT] = true;
		ST4.cells[0][1].connected[DOWN] = true;
		ST4.cells[0][2].filled = true;
		ST4.cells[0][2].connected[LEFT] = true;
		ST4.cells[1][1].filled = true;
		ST4.cells[1][1].connected[UP] = true;
		ST4.addVert(ST4.cells[1][1]);
		ST4.addHoriz(ST4.cells[0][0]);
		ST4.addHoriz(ST4.cells[0][2]);
		tertiaryPieces.add(ST4);
		
		//  X
		// XXX
		Piece ST5 = new Piece(2, 3);
		ST5.name = "ST5";
		ST5.rowOffset = 1;
		ST5.cells[1][0].filled = true;
		ST5.cells[1][0].connected[RIGHT] = true;
		ST5.cells[1][1].filled = true;
		ST5.cells[1][1].connected[LEFT] = true;
		ST5.cells[1][1].connected[RIGHT] = true;
		ST5.cells[1][1].connected[UP] = true;
		ST5.cells[1][2].filled = true;
		ST5.cells[1][2].connected[LEFT] = true;
		ST5.cells[0][1].filled = true;
		ST5.cells[0][1].connected[DOWN] = true;
		ST5.addVert(ST5.cells[0][1]);
		ST5.addHoriz(ST5.cells[1][0]);
		ST5.addHoriz(ST5.cells[1][2]);
		tertiaryPieces.add(ST5);
		
		//  X
		// XX
		//  X
		Piece ST6 = new Piece(3, 3);
		ST6.name = "ST6";
		ST6.rowOffset = 1;
		ST6.cells[0][1].filled = true;
		ST6.cells[0][1].connected[DOWN] = true;
		ST6.cells[1][1].filled = true;
		ST6.cells[1][1].connected[LEFT] = true;
		ST6.cells[1][1].connected[UP] = true;
		ST6.cells[1][1].connected[DOWN] = true;
		ST6.cells[2][1].filled = true;
		ST6.cells[2][1].connected[UP] = true;
		ST6.cells[1][0].filled = true;
		ST6.cells[1][0].connected[RIGHT] = true;
		ST6.addVert(ST6.cells[0][1]);
		ST6.addVert(ST6.cells[2][1]);
		ST6.addHoriz(ST6.cells[1][0]);
		tertiaryPieces.add(ST6);
		
		// X
		// X
		// XX
		// X
		Piece TL1 = new Piece(4, 2);
		TL1.name = "TL1";
		TL1.rowOffset = 0;
		TL1.cells[0][0].filled = true;
		TL1.cells[0][0].connected[DOWN] = true;
		TL1.cells[1][0].filled = true;
		TL1.cells[1][0].connected[UP] = true;
		TL1.cells[1][0].connected[DOWN] = true;
		TL1.cells[2][0].filled = true;
		TL1.cells[2][0].connected[UP] = true;
		TL1.cells[2][0].connected[RIGHT] = true;
		TL1.cells[2][0].connected[DOWN] = true;
		TL1.cells[2][1].filled = true;
		TL1.cells[2][1].connected[LEFT] = true; 
		TL1.cells[3][0].filled = true;
		TL1.cells[3][0].connected[UP] = true;
		TL1.addVert(TL1.cells[0][0]);
		TL1.addVert(TL1.cells[3][0]);
		TL1.addHoriz(TL1.cells[2][1]);
		tertiaryPieces.add(TL1);
		
		// X
		// X
		// XX
		// X
		Piece TL2 = new Piece(TL1);
		TL2.name = "TL2";
		TL2.rowOffset = 1;

		tertiaryPieces.add(TL2);
		
		// X
		// X
		// XX
		// X
		Piece TL3 = new Piece(TL1);
		TL3.name = "TL3";
		TL3.rowOffset = 2;
		tertiaryPieces.add(TL3);

		
		//   X
		// XXXX
		Piece TL4 = new Piece(2, 4);
		TL4.name = "TL4";
		TL4.rowOffset = 1;
		TL4.cells[0][2].filled = true;
		TL4.cells[0][2].connected[DOWN] = true;
		TL4.cells[1][0].filled = true;
		TL4.cells[1][0].connected[RIGHT] = true;
		TL4.cells[1][1].filled = true;
		TL4.cells[1][1].connected[LEFT] = true;
		TL4.cells[1][1].connected[RIGHT] = true;
		TL4.cells[1][2].filled = true;
		TL4.cells[1][2].connected[UP] = true;
		TL4.cells[1][2].connected[LEFT] = true;
		TL4.cells[1][2].connected[RIGHT] = true;
		TL4.cells[1][3].filled = true;
		TL4.cells[1][3].connected[LEFT] = true;
		TL4.addVert(TL4.cells[0][2]);
		TL4.addHoriz(TL4.cells[1][0]);
		TL4.addHoriz(TL4.cells[1][3]);
		tertiaryPieces.add(TL4);
		 
		// XXXX
		//   X
		
		Piece TL5 = new Piece(2, 4);
		TL5.name = "TL5";
		TL5.rowOffset = 0;
		TL5.cells[0][0].filled = true;
		TL5.cells[0][0].connected[RIGHT] = true;
		TL5.cells[0][1].filled = true;
		TL5.cells[0][1].connected[LEFT] = true;
		TL5.cells[0][1].connected[RIGHT] = true;
		TL5.cells[0][2].filled = true;
		TL5.cells[0][2].connected[LEFT] = true;
		TL5.cells[0][2].connected[DOWN] = true;
		TL5.cells[0][2].connected[RIGHT] = true;
		TL5.cells[0][3].filled = true;
		TL5.cells[0][3].connected[LEFT] = true;
		TL5.cells[1][2].filled = true;
		TL5.cells[1][2].connected[UP] = true;	
		TL5.addVert(TL5.cells[1][2]);
		TL5.addHoriz(TL5.cells[0][0]);
		TL5.addHoriz(TL5.cells[0][3]);
		tertiaryPieces.add(TL5);
		
		//  X
		// XX
		//  X
		// 	X
		Piece TL6 = new Piece(4, 2);
		TL6.name = "TL6";
		TL6.rowOffset = 0;
		TL6.cells[0][0].filled = true;
		TL6.cells[0][0].connected[DOWN] = true;
		TL6.cells[1][0].filled = true;
		TL6.cells[1][0].connected[RIGHT] = true;
		TL6.cells[1][1].filled = true;
		TL6.cells[1][1].connected[LEFT] = true;
		TL6.cells[1][1].connected[DOWN] = true;
		TL6.cells[1][1].connected[UP] = true;
		TL6.cells[2][1].filled = true;
		TL6.cells[2][1].connected[UP] = true;
		TL6.cells[2][1].connected[DOWN] = true;
		TL6.cells[3][1].filled = true;
		TL6.cells[3][1].connected[UP] = true;
		TL6.addVert(TL6.cells[0][1]);
		TL6.addVert(TL6.cells[3][1]);
		TL6.addHoriz(TL6.cells[1][0]);
		tertiaryPieces.add(TL6);
		
		//  X
		//  X
		// XX
		//  X
		Piece TL7 = new Piece(4, 2);
		TL7.name = "TL7";
		TL7.rowOffset = 0;
		TL7.cells[0][1].filled = true;
		TL7.cells[0][1].connected[DOWN] = true;
		TL7.cells[1][1].filled = true;
		TL7.cells[1][1].connected[UP] = true;
		TL7.cells[1][1].connected[DOWN] = true;
		TL7.cells[2][1].filled = true;
		TL7.cells[2][1].connected[UP] = true;
		TL7.cells[2][1].connected[LEFT] = true;
		TL7.cells[2][1].connected[DOWN] = true;
		TL7.cells[2][0].filled = true;
		TL7.cells[2][0].connected[RIGHT] = true;
		TL7.cells[3][1].filled = true;
		TL7.cells[3][1].connected[UP] = true;
		TL7.addVert(TL7.cells[0][1]);
		TL7.addVert(TL7.cells[3][1]);
		TL7.addHoriz(TL7.cells[2][0]);
		tertiaryPieces.add(TL7);
		
		// X
		// XX
		// X
		// X
		Piece TL8 = new Piece(4, 2);
		TL8.name = "TL8";
		TL8.rowOffset = 0;
		TL8.cells[0][0].filled = true;
		TL8.cells[0][0].connected[DOWN] = true;
		TL8.cells[1][0].filled = true;
		TL8.cells[1][0].connected[DOWN] = true;
		TL8.cells[1][0].connected[RIGHT] = true;
		TL8.cells[1][0].connected[UP] = true;
		TL8.cells[1][1].filled = true;
		TL8.cells[1][1].connected[LEFT] = true;
		TL8.cells[2][0].filled = true;
		TL8.cells[2][0].connected[UP] = true;
		TL8.cells[2][0].connected[DOWN] = true;
		TL8.cells[3][0].filled = true;
		TL8.cells[3][0].connected[UP] = true; 
		TL6.addVert(TL6.cells[0][0]);
		TL6.addVert(TL6.cells[3][0]);
		TL6.addHoriz(TL6.cells[1][1]);
		tertiaryPieces.add(TL8);
		
		// X
		// XX
		// X
		// X
		Piece TL9 = new Piece(TL8);
		TL9.name = "TL9";
		TL9.rowOffset = 1;
		tertiaryPieces.add(TL9);
		
		// X
		// XX
		// X
		// X
		Piece TL10 = new Piece(TL8);
		TL10.name = "TL10";
		TL10.rowOffset = 2;
		tertiaryPieces.add(TL10);
		
		//  X
		// XXXX
		Piece TL11 = new Piece(2, 4);
		TL11.name = "TL11";
		TL11.rowOffset = 0;
		TL11.cells[1][0].filled = true;
		TL11.cells[1][0].connected[RIGHT] = true;
		TL11.cells[0][1].filled = true;
		TL11.cells[0][1].connected[DOWN] = true;
		TL11.cells[1][1].filled = true;
		TL11.cells[1][1].connected[UP] = true;
		TL11.cells[1][1].connected[RIGHT] = true;
		TL11.cells[1][1].connected[LEFT] = true;
		TL11.cells[1][2].filled = true;
		TL11.cells[1][2].connected[LEFT] = true;
		TL11.cells[1][2].connected[RIGHT] = true;
		TL11.cells[1][3].filled = true;
		TL11.cells[1][3].connected[LEFT] = true;
		TL11.addVert(TL11.cells[0][1]);
		TL11.addHoriz(TL11.cells[1][0]);
		TL11.addHoriz(TL11.cells[1][3]);
		tertiaryPieces.add(TL11);
		
		// XXXX
		//  X
		Piece TL12 = new Piece(2, 4);
		TL12.name = "TL12";
		TL12.rowOffset = 0;
		TL12.cells[0][0].filled = true;
		TL12.cells[0][0].connected[RIGHT] = true;
		TL12.cells[1][1].filled = true;
		TL12.cells[1][1].connected[UP] = true;
		TL12.cells[0][1].filled = true;
		TL12.cells[0][1].connected[DOWN] = true;
		TL12.cells[0][1].connected[RIGHT] = true;
		TL12.cells[0][1].connected[LEFT] = true;
		TL12.cells[0][2].filled = true;
		TL12.cells[0][2].connected[LEFT] = true;
		TL12.cells[0][2].connected[RIGHT] = true;
		TL12.cells[0][3].filled = true;
		TL12.cells[0][3].connected[LEFT] = true;
		TL12.addVert(TL12.cells[1][1]);
		TL12.addHoriz(TL12.cells[0][0]);
		TL12.addHoriz(TL12.cells[0][3]);
		tertiaryPieces.add(TL12);
		
		
		// X
		// X
		// XX
		// X
		Piece TL13 = new Piece(TL1);
		TL13.name = "TL13";
		TL13.rowOffset = 3;
		tertiaryPieces.add(TL13);
		
		// X
		// XX
		// X
		// X
		Piece TL14 = new Piece(TL8);
		TL14.name = "TL14";
		TL14.rowOffset = 3; 
		tertiaryPieces.add(TL14);
		
		Piece SQ1 = new Piece(2, 2);
		SQ1.name = "SQ1";
		SQ1.rowOffset = 0;
		SQ1.cells[0][0].filled = true;
		SQ1.cells[0][0].connected[RIGHT] = true;
		SQ1.cells[0][0].connected[DOWN] = true;
		SQ1.cells[0][1].filled = true;
		SQ1.cells[0][1].connected[LEFT] = true;
		SQ1.cells[0][1].connected[DOWN] = true;
		SQ1.cells[1][0].filled = true;
		SQ1.cells[1][0].connected[UP] = true;
		SQ1.cells[1][0].connected[RIGHT] = true;
		SQ1.cells[1][1].filled = true;
		SQ1.cells[1][1].connected[UP] = true;
		SQ1.cells[1][1].connected[LEFT] = true;
		SQ1.addVert(SQ1.cells[0][0]);
		SQ1.addVert(SQ1.cells[0][1]);
		SQ1.addVert(SQ1.cells[1][0]);
		SQ1.addVert(SQ1.cells[1][1]);
		SQ1.addHoriz(SQ1.cells[0][0]);
		SQ1.addHoriz(SQ1.cells[0][1]);
		SQ1.addHoriz(SQ1.cells[1][0]);
		SQ1.addHoriz(SQ1.cells[1][1]);
		tertiaryPieces.add(SQ1);
		
		//  X
		// XXX
		//  X
		Piece PLUS = new Piece(3, 3);
		PLUS.name = "PLUS";
		PLUS.rowOffset = 1;
		PLUS.cells[1][0].filled = true;
		PLUS.cells[1][0].connected[RIGHT] = true;
		PLUS.cells[0][1].filled = true;
		PLUS.cells[0][1].connected[DOWN] = true;
		PLUS.cells[1][2].filled = true;
		PLUS.cells[1][2].connected[LEFT] = true;
		PLUS.cells[2][1].filled = true;
		PLUS.cells[2][1].connected[UP] = true;
		PLUS.cells[1][1].filled = true;
		PLUS.cells[1][1].connected[UP] = true;
		PLUS.cells[1][1].connected[RIGHT] = true;
		PLUS.cells[1][1].connected[DOWN] = true;
		PLUS.cells[1][1].connected[LEFT] = true;
		PLUS.addVert(PLUS.cells[0][1]);
		PLUS.addVert(PLUS.cells[2][1]);
		PLUS.addHoriz(PLUS.cells[1][0]);
		PLUS.addHoriz(PLUS.cells[1][2]);
		tertiaryPieces.add(PLUS);
		tertiaryPieces.add(new Piece(PLUS));
		tertiaryPieces.add(new Piece(PLUS));
		
		Piece SQ2 = new Piece(SQ1);
		SQ2.name = "SQ2";
		SQ2.rowOffset = 1;
		tertiaryPieces.add(SQ2);
	}	
	
	public void addRandomPieceFirstRow(Maze maze, int row, int col) {
		ArrayList<Piece> tempPieces = new ArrayList<>();
		if(col > 0) {
			for(Piece p: tertiaryPieces) {
				pieces.add(p);
			}
		}
		for(Piece p: pieces) {
			if(p.canBePlaced(maze, row, col)) {
				tempPieces.add(p);
			} 
		}
		if(tempPieces.size() == 0) {
			for(Piece p: secondaryPieces) {
				if(p.canBePlaced(maze, row, col)) {
					tempPieces.add(p);
				}
			}		
		}
	if(tempPieces.size() == 0 && (col > 0 || (row == 0 || row == 8 || row == 2) || row == 5)) {
			for(Piece p: specialPieces) {
				if(p.canBePlaced(maze, row, col)) {
					tempPieces.add(p);
				} 
			}	
		}
	if(col < 3 && row == 0 && (col == 0 ||maze.cells[row][col - 1].connected[UP] == false)) {
		tempPieces.add(edgePiece1);
	} else if(col < 3 && row == maze.cells.length - 1 && (col == 0 ||maze.cells[row][col - 1].connected[DOWN] == false)) {
		tempPieces.add(edgePiece2);
	}
	if(tempPieces.size() > 0) {		
		int rand = (new Random()).nextInt(tempPieces.size());
		tempPieces.get(rand).addTo(maze, row, col );
		System.out.println("Placed " + tempPieces.get(rand).name + " at " + row + " " + col);
		} else {
			System.out.println("Couldn't place at " + row + " " + col);

		}
		
	}
}
