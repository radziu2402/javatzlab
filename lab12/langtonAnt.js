var posX = -1;
var posY = -1;
var currentDirection = 'NORTH';

function moveAnt(direction, maxWidth, maxHeight) {
    switch (direction) {
        case 'NORTH':
            posY = (posY - 1 + maxHeight) % maxHeight;
            break;
        case 'EAST':
            posX = (posX + 1) % maxWidth;
            break;
        case 'SOUTH':
            posY = (posY + 1) % maxHeight;
            break;
        case 'WEST':
            posX = (posX - 1 + maxWidth) % maxWidth;
            break;
    }
}

function rotateClockwise(dir) {
    switch (dir) {
        case 'NORTH': return 'EAST';
        case 'EAST': return 'SOUTH';
        case 'SOUTH': return 'WEST';
        case 'WEST': return 'NORTH';
    }
}

function processGrid(cellMatrix) {
    var gridData = cellMatrix.getMap();
    var gridHeight = cellMatrix.getHeight();
    var gridWidth = cellMatrix.getWidth();
    if (posX === -1) {
        posX = Math.floor(gridHeight / 2);
        posY = Math.floor(gridWidth / 2);
    }

    if (!gridData[posX][posY]) {
        cellMatrix.activateCell(posX, posY);
        currentDirection = rotateClockwise(currentDirection);
    } else {
        cellMatrix.deactivateCell(posX, posY);
        currentDirection = rotateCounterClockwise(currentDirection);
    }

    moveAnt(currentDirection, gridWidth, gridHeight);
}

function rotateCounterClockwise(dir) {
    switch (dir) {
        case 'NORTH': return 'WEST';
        case 'WEST': return 'SOUTH';
        case 'SOUTH': return 'EAST';
        case 'EAST': return 'NORTH';
    }
}
