set title '4_Gaussian_6_3_UniformAL_robusness: Robustness index for 5 runs (avg = 62.46021505376344)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:4]
set yrange [-100:100]
plot "4_Gaussian_6_3_UniformAL_robusness_robustness.dat" using 1 notitle with line

