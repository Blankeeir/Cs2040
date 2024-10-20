import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class AlmostUnionFindFast{
    static int find(int x, int[] parent){
        if(parent[x] != x){
            parent[x] = find(parent[x], parent);
        }
        return parent[x];
    }

  
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringBuilder inputSb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null){
            inputSb.append(line).append(" ");
        }
        String[] tokens = inputSb.toString().split("\\s+");
        int ptr =0;
        while(ptr < tokens.length){
            if(ptr +1 >= tokens.length){
                break;
            }
            int n = Integer.parseInt(tokens[ptr++]);
            int m = Integer.parseInt(tokens[ptr++]);
            // Initialize parent, size, sum arrays
            // Sets are from n+1 to 2n
            int sizeLength = 2*n +1;
            int[] parent = new int[sizeLength];
            long[] size = new long[sizeLength];
            long[] sum = new long[sizeLength];
            for(int i=1;i<=n;i++){
                int set_id = i +n;
                parent[i] = set_id;
                parent[set_id] = set_id;
                size[set_id] =1;
                sum[set_id] =i;
            }
            for(int j=0; j<m; j++){
                if(ptr >= tokens.length){
                    break;
                }
                int type = Integer.parseInt(tokens[ptr++]);
                if(type ==1){
                    if(ptr +1 >= tokens.length){
                        break;
                    }
                    int p = Integer.parseInt(tokens[ptr++]);
                    int q = Integer.parseInt(tokens[ptr++]);
                    int set_p = find(parent[p], parent);
                    int set_q = find(parent[q], parent);
                    if(set_p != set_q){
                        parent[set_p] = set_q;
                        size[set_q] += size[set_p];
                        sum[set_q] += sum[set_p];
                    }
                }
                else if(type ==2){
                    if(ptr +1 >= tokens.length){
                        break;
                    }
                    int p = Integer.parseInt(tokens[ptr++]);
                    int q = Integer.parseInt(tokens[ptr++]);
                    int set_p = find(parent[p], parent);
                    int set_q = find(parent[q], parent);
                    if(set_p != set_q){
                        size[set_p]--;
                        sum[set_p] -=p;
                        size[set_q]++;
                        sum[set_q] +=p;
                        parent[p] = set_q;
                    }
                }
                else if(type ==3){
                    if(ptr >= tokens.length){
                        break;
                    }
                    int p = Integer.parseInt(tokens[ptr++]);
                    int set_p = find(parent[p], parent);
                    sb.append(size[set_p]).append(" ").append(sum[set_p]).append("\n");
                }
            }
        }
        System.out.print(sb.toString());
    }
}
