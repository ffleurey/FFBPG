set title 'dist_nb_links_per_servers (AVG = 0.18796992481203006)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:265]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

