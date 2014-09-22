set title '3_UniformNExp_2_1_AL_robusness: Robustness index for 5 runs (avg = 76.47311827956992)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:4]
set yrange [-100:100]
plot "3_UniformNExp_2_1_AL_robusness_robustness.dat" using 1 notitle with line

