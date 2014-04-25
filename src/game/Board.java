package game;

public class Board {

	private Piece[][] checker = new Piece[8][8];

	private boolean turnWhite = true;
	private boolean turnBlack = false;
	
	public Piece[][] getChecker() {
		return this.checker;
	}

	public void setChecker(Piece[][] newChecker) {
		this.checker = newChecker;
	}
	
	public Board(Piece[][] matrix)
	{
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				this.checker[i][j] = matrix[i][j];	//static no more
			}
		}
	}
	
	public void printBoard()
	{
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("\ta\tb\tc\td\te\tf\tg\th");
		for(int i=0; i<8; i++)
		{
			System.out.print(i + "\t");
			for(int j=0; j<8; j++)
			{
				if(this.checker[i][j] != null)	//prevent null pointer error
				{
					switch(this.checker[i][j])	//knight and bishop are too long words
					{
						case Bishop_W:
						{
							System.out.print("Bshop_W"+"\t");
							break;
						}
						case Bishop_B:
						{
							System.out.print("Bshop_B"+"\t");
							break;
						}
						case Knight_W:
						{
							System.out.print("Knght_W"+"\t");
							break;
						}
						case Knight_B:
						{
							System.out.print("Knght_B"+"\t");
							break;
						}
						default:
							System.out.print(this.checker[i][j]+"\t");
							break;
					}
				}
				else
				{
					System.out.print(" |O|" + "\t");
				}
			}
			System.out.print((7-i) + "\t");	//real chess numbers != java line printing numbers
			System.out.println();
		}
		System.out.println("\ta\tb\tc\td\te\tf\tg\th");
		System.out.println("-------------------------------------------------------------------------");
	}
	
	public boolean movePiece(int startRow, int startCol, int endRow, int endCol)
	{
		if(isMoveLegal(startRow, startCol, endRow, endCol, true))	//if move is legal, if king isn't under threat or is going to be threatened because of this move
		{
			en_passant_check();
			isMoveLegal(startRow, startCol, endRow, endCol, true);
			this.checker[endRow][endCol] = this.checker[startRow][startCol];
			this.checker[startRow][startCol] = null;
			switchTurn();
			printBoard();
			return true;
		}
		else
		{
			System.out.println("move not legal");
			printBoard();
			return false;
		}
	}
	
	public boolean isMoveLegal(int startRow, int startCol, int endRow, int endCol, boolean wetRun)
	{
		Piece piece = checker[startRow][startCol];
//		Piece endTile = checker[endRow][endCol];
		
		if(piece == null)
		{
			//cancel graphic highlight of tile
			return false;
		}
		else if((piece.alligence == "White" && turnBlack) || (piece.alligence == "Black" && turnWhite))
		{
			if(wetRun)
			{
				System.out.println("Not your turn!");
			}
			
			return false;
		}
		else
		{
			switch(piece)
			{
				case Pawn_B:
					return pawnMove(startRow, startCol, endRow, endCol, wetRun);
//					break;
				case Pawn_W:
					return pawnMove(startRow, startCol, endRow, endCol, wetRun);
//					break;
					
				case Bishop_B:
					return bishopMove(startRow, startCol, endRow, endCol);
//					break;
				case Bishop_W:
					return bishopMove(startRow, startCol, endRow, endCol);
//					break;
					
				case Knight_B:
					return knightMove(startRow, startCol, endRow, endCol);
//					break;
				case Knight_W:
					return knightMove(startRow, startCol, endRow, endCol);
//					break;
					
				case Rook_B:
					return rookMove(startRow, startCol, endRow, endCol);
//					break;
				case Rook_W:
					return rookMove(startRow, startCol, endRow, endCol);
//					break;
					
				case Queen_B:
					return queenMove(startRow, startCol, endRow, endCol);
//					break;
				case Queen_W:
					return queenMove(startRow, startCol, endRow, endCol);
//					break;
					
				case King_B:
					return kingMove(startRow, startCol, endRow, endCol);
//					break;
				case King_W:
					return kingMove(startRow, startCol, endRow, endCol);
//					break;
					
				default:
					return false;
//					break;
			}
//			return true;
		}
	}
	
	public boolean pawnMove(int startRow, int startCol, int endRow, int endCol, boolean wetRun)
	{
		Piece piece = checker[startRow][startCol];
		Piece endTile = checker[endRow][endCol];
		Piece en_passant;
		Piece en_passant_enemy;
		int pawnStartRow;
		int moveDir;
		
		if(piece.alligence == "White")
		{
			pawnStartRow = 6;
			moveDir = -1;
			en_passant = Piece.En_Passant_W;
			en_passant_enemy = Piece.En_Passant_B;
		}
		else
		{
			pawnStartRow = 1;
			moveDir = 1;
			en_passant = Piece.En_Passant_B;
			en_passant_enemy = Piece.En_Passant_W;
		}
		
		if(endTile == null)	//only move
		{
			if(endRow == startRow+moveDir && startCol == endCol)	//normal movement
			{
				return true;
			}
			else if((checker[startRow+moveDir][startCol] == null) && startRow == pawnStartRow && endRow == startRow+(moveDir*2) && startCol == endCol)	//double tile movement
			{
				if(wetRun)
				{
					checker[startRow][startCol] = en_passant;
				}
				return true;
			}
			else if(checker[endRow-moveDir][endCol] == en_passant_enemy)	//en passant
			{
				if(checker[endRow-moveDir][endCol] == en_passant_enemy && ((endCol == startCol+1)||(endCol == startCol-1)) && endRow == startRow+moveDir)	//diagonal forward attack movement
				{
					if(wetRun)
					{
						checker[endRow-moveDir][endCol] = null;
					}
					return true;
				}
				else
				{
					return false;
				}
			}
			else	//illegal movement
			{
				return false;
			}
		}
		else if(endTile.alligence != piece.alligence)	//move and attack
		{
			if((endCol == startCol+1)||(endCol == startCol-1) && endRow == startRow+moveDir)	//diagonal forward attack movement
			{
				return true;
			}
			else	//illegal attack move
			{
				return false;
			}
		}
		else	//tile occupied by ally
		{
			return false;
		}
	}
	
	public boolean bishopMove(int startRow, int startCol, int endRow, int endCol)
	{
		Piece piece = checker[startRow][startCol];
		Piece endTile = checker[endRow][endCol];
				
		if(endTile == null  ||  endTile.alligence != piece.alligence)	//only move or move and attack
		{
			if(Math.abs(endCol-startCol) == Math.abs(endRow-startRow))	//diagonal movement
			{
				int colIndexPar;	//horizontal direction
				if(endCol>startCol)
				{
					colIndexPar = 1;
				}
				else
				{
					colIndexPar = -1;
				}
				
				int rowIndexPar;	//vertical direction
				if(endRow>startRow)
				{
					rowIndexPar = 1;
				}
				else
				{
					rowIndexPar = -1;
				}
				
				for(int i=1; i<Math.abs(endCol-startCol); i++)	//check for free path
				{
					if(checker[startRow+i*rowIndexPar][startCol+i*colIndexPar] != null)
					{
						return false;
					}
				}
				return true;
			}
			else	//illegal movement
			{
				return false;
			}
		}
		else	//tile occupied by ally
		{
			return false;
		}
	}
	
	public boolean knightMove(int startRow, int startCol, int endRow, int endCol)	//NEED TO FIX, maybe the rest below are bugged too
	{
		Piece piece = checker[startRow][startCol];
		Piece endTile = checker[endRow][endCol];
		
		int horizAbs = Math.abs(endRow-startRow);
		int vertAbs = Math.abs(endCol-startCol);
		
		if(endTile == null  ||  endTile.alligence != piece.alligence)	//only move or move and attack
		{
			if((horizAbs <= 2 || horizAbs <= 1) && (Math.abs(horizAbs-vertAbs)) == 1)	//normal movement
			{
				return true;
			}
			else	//illegal movement
			{
				return false;
			}
		}
		else	//tile occupied by ally
		{
			return false;
		}
	}
	
	public boolean rookMove(int startRow, int startCol, int endRow, int endCol)
	{
		Piece piece = checker[startRow][startCol];
		Piece endTile = checker[endRow][endCol];
				
		if(endTile == null  ||  endTile.alligence != piece.alligence)	//only move or move and attack
		{
			int colIndexPar;	//horizontal direction
			if(endCol>startCol)
			{
				colIndexPar = 1;
			}
			else
			{
				colIndexPar = -1;
			}
			
			int rowIndexPar;	//vertical direction
			if(endRow>startRow)
			{
				rowIndexPar = 1;
			}
			else
			{
				rowIndexPar = -1;
			}
			
			if(endCol == startCol)
			{
				for(int i=1; i<Math.abs(endRow-startRow); i++)	//check for free path
				{
					if(checker[startRow+i*rowIndexPar][startCol] != null)
					{
						return false;
					}
				}
				return true;
			}
			else if(endRow == startRow)
			{
				for(int i=1; i<Math.abs(endCol-startCol); i++)	//check for free path
				{
					if(checker[startRow][startCol+i*colIndexPar] != null)
					{
						return false;
					}
				}
				return true;
			}
			else	//illegal move, not a straight line
			{
				return false;
			}
		}
		else	//tile occupied by ally
		{
			return false;
		}
	}
	
	public boolean queenMove(int startRow, int startCol, int endRow, int endCol)
	{
		Piece piece = checker[startRow][startCol];
		Piece endTile = checker[endRow][endCol];
				
		if(endTile == null  ||  endTile.alligence != piece.alligence)	//only move or move and attack
		{
			int colIndexPar;	//horizontal direction
			if(endCol>startCol)
			{
				colIndexPar = 1;
			}
			else
			{
				colIndexPar = -1;
			}
			
			int rowIndexPar;	//vertical direction
			if(endRow>startRow)
			{
				rowIndexPar = 1;
			}
			else
			{
				rowIndexPar = -1;
			}
			
			if(Math.abs(endCol-startCol) == Math.abs(endRow-startRow))	//diagonal movement
			{				
				for(int i=1; i<Math.abs(endCol-startCol); i++)	//check for free path
				{
					if(checker[startRow+i*rowIndexPar][startCol+i*colIndexPar] != null)
					{
						return false;
					}
				}
				return true;
			}
			if(endCol == startCol)
			{
				for(int i=1; i<Math.abs(endRow-startRow); i++)	//check for free path
				{
					if(checker[startRow+i*rowIndexPar][startCol] != null)
					{
						return false;
					}
				}
				return true;
			}
			else if(endRow == startRow)
			{
				for(int i=1; i<Math.abs(endCol-startCol); i++)	//check for free path
				{
					if(checker[startRow][startCol+i*colIndexPar] != null)
					{
						return false;
					}
				}
				return true;
			}
			else	//illegal movement
			{
				return false;
			}
		}
		else	//tile occupied by ally
		{
			return false;
		}
	}
	
	public boolean kingMove(int startRow, int startCol, int endRow, int endCol)
	{
		Piece piece = checker[startRow][startCol];
		Piece endTile = checker[endRow][endCol];
		
		if(endTile == null  ||  endTile.alligence != piece.alligence)	//only move or move and attack
		{
			if(Math.abs(endCol-startCol) <= 1 && Math.abs(endRow-startRow) <= 1)	//1 step movement
			{
				return true;
			}
			else	//if rook-king switch *** NEED TO ADD ***
			{
				return false;
			}
		}
		else	//tile occupied by ally
		{
			return false;
		}
	}
	
	public void switchTurn()
	{
		turnWhite = !turnWhite;
		turnBlack = !turnBlack;
	}
	
	public String whosTurn()
	{
		if(turnWhite)
		{
			return "White";
		}
		else
		{
			return "Black";
		}
	}
	
	private void en_passant_check()
	{
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				if(checker[i][j] == Piece.En_Passant_W)
				{
					checker[i][j] = Piece.Pawn_W;
				}
				else if(checker[i][j] == Piece.En_Passant_B)
				{
					checker[i][j] = Piece.Pawn_B;
				} 
			}
		}
	}
}
