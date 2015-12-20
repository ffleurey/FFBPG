set title 'dist_nb_links_per_servers (AVG = 0.23654211526282456)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:3157]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

