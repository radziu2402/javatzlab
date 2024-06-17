function processGrid(cellMatrix) {
    var gridWidth = cellMatrix.getWidth();
    var gridHeight = cellMatrix.getHeight();
    var gridData = cellMatrix.getMap();

    var updatedMap = [];
    for (var x = 0; x < gridWidth; x++) {
        updatedMap[x] = [];
        for (var y = 0; y < gridHeight; y++) {
            var consensusRatio = calculateConsensus(gridData, x, y, gridWidth, gridHeight);
            updatedMap[x][y] = consensusRatio > 0.5; // Komórka staje się aktywna, jeśli większość sąsiadów jest aktywna
        }
    }

    // Zastosowanie nowej mapy do aktualizacji stanu siatki
    for (var x = 0; x < gridWidth; x++) {
        for (var y = 0; y < gridHeight; y++) {
            if (updatedMap[x][y]) {
                cellMatrix.activateCell(x, y);
            } else {
                cellMatrix.deactivateCell(x, y);
            }
        }
    }
}

function calculateConsensus(grid, posX, posY, maxWidth, maxHeight) {
    var activeCount = 0;
    var totalCount = 0;
    for (var offsetX = -1; offsetX <= 1; offsetX++) {
        for (var offsetY = -1; offsetY <= 1; offsetY++) {
            var neighborX = posX + offsetX;
            var neighborY = posY + offsetY;
            if (neighborX >= 0 && neighborX < maxWidth && neighborY >= 0 && neighborY < maxHeight) {
                totalCount++;
                if (grid[neighborX][neighborY]) activeCount++;
            }
        }
    }
    return activeCount / totalCount;  // Zwraca stosunek aktywnych komórek do wszystkich sąsiadujących komórek
}
