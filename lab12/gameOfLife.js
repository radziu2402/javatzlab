function countActiveNeighbors(grid, posX, posY, maxWidth, maxHeight) {
    var neighborCount = 0;
    for (var offsetX = -1; offsetX <= 1; offsetX++) {
        for (var offsetY = -1; offsetY <= 1; offsetY++) {
            if (offsetX === 0 && offsetY === 0) continue;
            var neighborX = posX + offsetX;
            var neighborY = posY + offsetY;
            if (neighborX >= 0 && neighborX < maxWidth && neighborY >= 0 && neighborY < maxHeight && grid[neighborX][neighborY]) {
                neighborCount++;
            }
        }
    }
    return neighborCount;
}

function processGrid(cellMatrix) {
    var gridWidth = cellMatrix.getWidth();
    var gridHeight = cellMatrix.getHeight();
    var gridData = cellMatrix.getMap();

    var updatedMap = [];
    for (var x = 0; x < gridWidth; x++) {
        updatedMap[x] = [];
        for (var y = 0; y < gridHeight; y++) {
            var activeNeighbors = countActiveNeighbors(gridData, x, y, gridWidth, gridHeight);
            if (gridData[x][y]) {
                updatedMap[x][y] = activeNeighbors === 2 || activeNeighbors === 3;
            } else {
                updatedMap[x][y] = activeNeighbors === 3;
            }
        }
    }

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
