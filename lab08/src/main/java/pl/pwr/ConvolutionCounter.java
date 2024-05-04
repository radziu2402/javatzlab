package pl.pwr;

public class ConvolutionCounter {

    public native Double[][] countInCpp(Double[][] input, Double[][] filter);

    public Double[][] countInJava(Double[][] input, Double[][] kernel) {
        int inputHeight = input.length;
        int inputWidth = input[0].length;
        int kernelHeight = kernel.length;
        int kernelWidth = kernel[0].length;

        Double[][] rotatedKernel = new Double[kernelHeight][kernelWidth];
        for (int y = 0; y < kernelHeight; y++) {
            for (int x = 0; x < kernelWidth; x++) {
                rotatedKernel[y][x] = kernel[kernelHeight - 1 - y][kernelWidth - 1 - x];
            }
        }

        int padHeight = kernelHeight / 2;
        int padWidth = kernelWidth / 2;

        Double[][] output = new Double[inputHeight][inputWidth];

        for (int i = 0; i < inputHeight; i++) {
            for (int j = 0; j < inputWidth; j++) {
                output[i][j] = 0.0;
            }
        }

        for (int y = 0; y < inputHeight; y++) {
            for (int x = 0; x < inputWidth; x++) {
                for (int ky = 0; ky < kernelHeight; ky++) {
                    for (int kx = 0; kx < kernelWidth; kx++) {
                        int pY = y + ky - padHeight;
                        int pX = x + kx - padWidth;

                        if (pY >= 0 && pY < inputHeight && pX >= 0 && pX < inputWidth) {
                            output[y][x] += rotatedKernel[ky][kx] * input[pY][pX];
                        }
                    }
                }
            }
        }

        return output;
    }

//    public Double[][] countInJava(Double[][] input, Double[][] kernel) {
//        int inputRows = input.length;
//        int inputCols = input[0].length;
//        int kernelRows = kernel.length;
//        int kernelCols = kernel[0].length;
//
//        int outputRows = inputRows - kernelRows + 1;
//        int outputCols = inputCols - kernelCols + 1;
//        Double[][] output = new Double[outputRows][outputCols];
//
//        for (int i = 0; i < outputRows; i++) {
//            for (int j = 0; j < outputCols; j++) {
//                output[i][j] = 0.0;
//            }
//        }
//
//        for (int i = 0; i < outputRows; i++) {
//            for (int j = 0; j < outputCols; j++) {
//                double sum = 0.0;
//                for (int m = 0; m < kernelRows; m++) {
//                    for (int n = 0; n < kernelCols; n++) {
//                        int inputX = i + m;
//                        int inputY = j + n;
//                        sum += input[inputX][inputY] * kernel[m][n];
//                    }
//                }
//                output[i][j] = sum;
//            }
//        }
//        return output;
//    }
}
