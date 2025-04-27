package spring.ai.mcp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageTool {

    final String base64Image = "iVBORw0KGgoAAAANSUhEUgAAAQ4AAABoCAIAAACPCYkBAAAAAXNSR0IArs4c6QAAAERlWElmTU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAA6ABAAMAAAABAAEAAKACAAQAAAABAAABDqADAAQAAAABAAAAaAAAAABE0A32AAAkqElEQVR4Ae1dB3xURRM3YAELSLdRLXQQ6VVK6L2EEggtIYXQey+hE1qAEDoECEUQkCIgUhSk96YiTdFPRYp0UNHvHxcnc/vqvXuXXODulx/Mzk7bfTtv2+w+n3/++ecZ789bA94aMKqBZ0Gg6i0+Pj5GvN78p7QGlA3GWmvxEDlKM1Sfq8/ff/+tmiGQ1qpAR6A3K1nXgH6rMt9aPEqOvjH0vFIQpAqYlKLK60U+STWAlmDYGAwJRIUYkhkSQA5oDMkMCZx6QA6ugreC8sVgrz6njPMSe3INWGstyuZkTY6yZuySo5QsMPFzFfy4hwhYWSRB6f33KawBqTHY1Vr05UApJ+DVbqM9XJSWOqE6vlfRp+AmemFvDai2Fo7kjU+qLp7FWYhMFUm5qoAqC0dypaoSTCJTcKGcRwvPabzw01YDntYqbLTHUJTDXEXrwdvll1ryvXhPrgFPe/pJZY+mq3CDDB3Ok5+01zYPrAGdFsWzeCNM8lI8ntYnuR1eAzy2BtB2bWmytghBLdllD0RJJklJ8UTIdVVcRWIgUo99ll7D3F0Dhm1AajPJ0R6tIgg8aiDBVVRJDevI3ZXilZ/sasDT2owt9sA7Elwl2T0Sr8EeUgOqL9kktM2yPZJTSXIMXEVQSyKSsBa8qj2qBqTGBNuStqm4Yo/ScmC4wARX4aScAuVHkud61NPyGpNUNSA1EpiRtI3Egj2GBoOAxCa4Cq9xIYKIeJYX9taAsmEYtjm3Vlri2KO5r+LWsnmFJ98aSJx2ab5+3G0PvQXUexVhKO99YBDxmC+Gl/JJqgF3N0pn6yqR7fH2Ks4+oKeU3vV2yV+1Smmq1cpZJAKlBB1iiddaUq9X4RLdbQfX5YU9vAbc2hiUPmBYG9bs4Yp0JBCZXq9CRIa2egme7BrgLUGnVSVaJSSJPSm41kQrqleRtwZUG54q0k11xX1eSy/Hx/cqPC3MAkaJdJPFXrEeXgO8JfDmZcFsiZ1LhjQpKRGTOk6mRUPE5gEuVnBJmMdzFQkrKbDRIEnyE5zcs2ffT//7mQqYNm2aalWrUPLkydPvvffOCy+8QBj3Affu3du0eSvsGTN6+PPPP++KIv12QpJVGwzuBrr8409cwssvvZQhQ3richa4efPW7zdvcq5MmTK+mDo1xwhY1R5kAc/t4bBSiPG0XksNyfrzzz8fPXqUKlUqwiQ+MGr0+B07d5Pe8uVKDR0ygJI6wO3btxctXhoSHPjss8ZVoSNHmbVwUdzWz3cQ/r133xGucufOnfGRUXHLVtSqUXVq1ATD6iUJzgJomnv37l+9Zt3mLZ/fu38f7K+9lqV3r27OyrGL/ubNmxUr1+TS/Jo0GDdmBMcQbKZaFsQunjptJrEAiImeXL2aL8cYwpK3qNILY/TahxlzHzx4ENqx2+3bd+bOmZ7u1VdVNSUC8udffzt/4QIpeuedXATrAHgttWkXcvzEyb37DsRET3nllVd0iJ3NSpnSoW4fPYq/bw2tdujw0VeuXAG84dMt2bJldV/bbRvYcdeu3dzsmbMX1KhetWDB/BzpabCZVmevzfreQvY8S5AF9Xfv3g3q0GnfgYPg9WsasHD+zLfeelOSc+XKb1986fDAJALDZKOG9VKmTGlI5izB77/fDGjT4dTpM2D8as/+xn6tFsyLefPNN5yVo0WfMoXD9Zx///0IlFu2fC78RHDNmDk3R/Zsfn6NtIS4go8Y2r9+w+a3bt8mIbChV5+BG9atfO655whpCLjSQtwhnOwhgLQAo0SKXIxCX3zxRaKUAC0uTubw5uMZhjBeye3ahx49fkJQnr9wsbFfy/nzYvLny8t5z52/0KffYI5xFq5Tu2bq1Da7yo3ff28ZEPT119+QMd+dO9+wcYu5s6MLFSpASFcAH0f3/uvfXmXsmOGXvr907PgpkjxgcET27NlKlChGGLuAHDmyT544NjCkE+bLJPPsd+eips7o1bMrYTwZ2LHzy1OnznTuFOqikceOn2jTNqRVy2aB7VunT29xdqS3r6Jv37jxk8hPBOWV3642a95m9+49+oyekHvh/MWffvxJsuS3q9ea+rfd+vl2CW8t+WwKB/cWvQrm8bNipr2WJQvJ/Ouvvzp37X3t2nXC2AhUrvxht85hksDZcxde/vFHCelRSUyvP9u6rW79pu2DOk6Kij7D3mjW7IyNXYreFX14mQrVhkeM/vnnXyzIse4qQ4f0x8RUUnn33r2gkM779scPyWz/rVy5umjx8vQ3afI0yyqKFi2yauWSNxXDxYcPHnTs1AMvM8uSiTGFNAD7t1dBbubMmWbPnPoCWwW58ttvXbv31r88msQ6C3TpHFa6VEnOhWWYqKgZHONRMHqAWnUah4R1FWNjdImRE6JcsfDq1WsbN20REvB8Fy5aWqeeH95Qzsq07ip4QU6fNik4qK2k8uHDhx2CO2ExVMK7nnzw8OH1Gzfo7869e67IfPedt9euWvp+YXm4hUoMC+/uurenSOlQt4/YNeqYWA8d1Jcbj8nSnLkLOcYuGKPwUSMGS2vEaz7ZgJGYXSrslZM5U6aLl77nMnd+sevAgUMc4xS8fMUqvB04S8MG9SwseFqfq0A3HkP/fr2yZn1r6PAxYoAhDLpz9y5WllYsj0Vz5CYCfv3111r5N5WQUjJy4lQJ46ZkxowZlsUt7NFrwKbNn3EV8PagDuFLFs99v3AhjncKTpnCoW7/+XdaTxJaNPfbseOLrdt2Cky1qpWb+jWkXHuBnDlzhIUGRk2NIbHowSZOmjorJpHqmfSaAd544/X2bVvFzJrHicdFTvl45RKOMQljGyNu6UecOEWKFK1bt+AYk7DD4zTJI5G1atn8zTdeD+vcE70bZWHe3LpN8JqPlxJGAFmyZOkYFiwhpWSiuQr0YjsoetrEseMmYgTPzcBIsk370BVxC/PkeY/jzcMpUzqsgInFYs4+buyI47Ua3rt3f8jgfn5N3OUnQmPH0A7r13964WLC2/qzrdsx1HHlXcDLYi/cMazDRyvXXLueMH87cvTY59t2+Fap5KyiLZ99/suvv3KuKpU/zJb1LY4xCdvgKtBUqdKH0VMnhIR1e/QoYQhYIH8+7MVeuHjJpClJRSb6Rkwl58yL5Tbcunnr5MlTll0lhbQC5tirQFG6dOlmTJ+cOUumrG/pPbn9Bw7u2GHD3An9OXcVGDBwYET58qV5kS3AlSpVKFmiuAVGHZaXX365a5ewIcNGcRr0is66Cp5pdMxcLgRwu7YBEsZk0h5XgbIqlSuOHT2sd99BQrFv5Uozoifpr99j+5KWMVOmTCGNp00WwC6yAf1737p1Z8XKj0ng8KEDxI5H2Qry6gXR6AA3f3eIubh167ahnD69utWvV1uSefz4yVlzFkhIW5JnvvkGfy6KSp8+ne2uApP8WzSNXbQUOxBkHmb5X321t2xZJ3x73bqNZ858TRIA5Mn9XulSJTjGPGybq0Blk8YNrl+/PmbcJDN+Avqy5atiji5sLVH8gxXLFpm32x2Uo0cNvXPn9sZN8fOWIYP6tg7wF1r+x0K5rOv95x9DOdgmsy7fkziDgsOPHn284UZ2KZf4Pln36bZtXxCBAKZNnVCmdElsOvfs3rlj5x48d9bsBeZdBVP5SMUaaft2FrsUmGGnq0BccIf2GTNmrFunpn5/wsvvOTAmfJMnjbtz9165sqUsd9OeU5wktASRdfQS1DHjjz/+uP7HHxIBLeNWr+6bM0d2vhq266s92GPJlzePxKKaXLxkubR1hkUmRH6oEptBOixommEwpIE1ydFPRLlg+bw50UGBbQ2L6SVwdw3gzRUU2EbSgo5Fwqgm4avTo2dJWf379nAlQsp+V5HsS3ZJV2oz2RXWww1u3Kh+powZuJEbNm7+URFmwQkEPHPWPKzBcnyZ0iWw+MQxzsKuDsB++ul/iCrPndviiqqz5iYJfcP6dS3oRVDZ4/3m/5hr16z2/PN6B1QQtfUfbcL/rVq1aKBhwC+//Fq/UfME0meeGTakf80a1TjGAhwc2gXR1pxx3ZoVWbJk5hiCX37lZYJtB7DN3aa1/4RJCWEZb+fKcePG78qoXEk1hnbYZKSxHHYAsWwj0TibdMlVLl363j8g8MH9B3GL5+XNm9tZ3cmFftLEMRZMxTEYyVWGDR2ITU9nReGskupxJchBjMz77xc+duw4ydy8ZVub1i0paQHAO/v4yYRoTkgoXqyIs3H7devUKvJ+YUk7JIslE8Lnfu/dih+Wo6QAsjpGG7Vq2SJm5jxsc2Gm0aVzaK2a1TEwk1iUyYED+rRu7Y/15TVrN2BzvFH9OlIUr5LFEGPdVc6e/Q5B7AiRhA7/Vu2xt+26NYbmukJw+PDRq9euGUp49dW0tqx+pmZRXkLp/Qf3DbU7S9CoQR3uKvv2Hzh//uLbb+d0Vg7R4ygYj0QGvlHD+pRrEsCutJJy8pTpErJQofz9+vaUkFISp0d7dO+cOXNGM05Cew8Qgt2qCeNHhYUETYuOsSWS2qKrnDp1unXbEBoO4txmq4CgxYvmYNtRKqrnJKOmz5KOOqnahvf0mlVxqllOIVMpTq4+ePDQKQlmiOvUrhExclzCSOOZZ5YsWzF0UD8zvEoa7NmtXrOe4zEEql2rOsdYhnFo2RqvKyu8eGtMmTTeml6Jy7gvkxhEctfuveQnAgNvadk6CC6kSv8UIl98MbVUaoxUJYzrSWz5f1ihLJezevU67O1yjHkYHe/3P/zA6X19K9tyOPTChYuYvHHJgE+f+RbhdhLSY5MWe5Ww0CAMGceOn8QLhkiQdoEdEfdlOOviXE8qrDIA+/eAu+3lbVC/zrbtCRt5t27dWrN2PcIxLSjiwQqCvXFDK0saStWbt6h0KdhK79q974zpk6TpBzZPOoY7bD4qBQJz46bDGhcwQ4eNHDvOoU0qGf2a1A/vGKLEG2IsugrkhgS3x4tzyPDRfGiLyQCO4yAI1JZXkaH1nkzw0ksvSebhEIGEsSWJyKg0adPgPUXSpkyNwaJZ6tTOXQyCYIK1n2wkIQAQD1+hvEOXxXOdgjdudIjdJl6EMw4dPmrE8MGEAYCuRurceK4OLGbOOgTIunZDdjB9esq1OAAT/AGtWkSOHZHC8bgf+tnwTj0R/Ew6nk4gTZpXpILfu+uWuBUERwc7btXh+P68+bGSdsNk9Mw5fM4D+g5BbWzZZcK4TifYbEnciugZ8nahobWJT2C9VxG2Iu4rVaoXunbvx8+rIABh8LCRo0cMTfzy6GicMG7EfbVBfI+e/RHjrcOILNwQgPs09Gl4LoIvlP3qrdsJL35O7Drctk2refMX89kj9uCaN2tifm0aZ2hXrlzDLcmQPr2/fzOOsQwvjF2iz4udE5zOQFvSJ0vaXFddBdbjmogrV66OGDWOl+Tbb77DNlDSBgtzewBjF0LCiGSqVM+r4jly9pz5K1et5Rh9OHbBrFIli0s0CC6WMHYlMdjDeJhPHbERMXlq9KiIISZVYO8C8YWcGDc2aO3ncDJDGE64acs2Q7L+A4dlzfqmLcv0hrqsEdjgKlCM5bxz584vW7FKGFGkcKHYBTM9yk+s1Y4rXCg+Vlr5Co/7XAV2BgS0mDsvlm8cLV/+cYB/czPnbdCapQk9Jj+tA1q4UnziXbxkGT/FRPh4wMeHJroY+2Eqv3bNMuyH4EK52rVrOlBaTfz5x0McYrPK7cBnj6tAZMTwQZe+v7x33/5CBQvELpyF0zkOep7KRNq0afmtX1ibcl81oAcIDWk/cnQkqcCQuGv3Pp+sWW548eeQoaMwBCBGAIFtA5TLEpzAJIwbsOKWr9Qirl61ytFjJ6iKEIyMa+VWr4pDgM/0qISCaLGbwd+4ccMuV3FpWs9tRcgNVv0Q5rRo4WzlMJ1TPj2wdCEvjo65tewt/ZvhqCNXgbsmRo0xaHMbP938+fYdnAtdCiY/HGMZnh49k5bmlBctpEnzcsSwAVw4DIZ7K0+2cJqkgm1zFRQAISG4wwWRCElVGE/Ti6M73CQzRzg4vbMweo9xYyIkLqwv6dxshrf+8Ag5wm1A357K5TtJrJnkD5d/jF28jCgDWjUnmABcMYy7YSkJAHtEM2fJp3w5QVLBdrqKs2VAGIWzLElFP2zIgCMHd9EfTn5Llmxcv4pyAeAcHwgyZUjHycys+nN6C3D5cmVwh6LE2LffEOkqBiIYPWYCLgqkJICSxYs1teli2MjIybRUkDlTRq2Lt3Ewmw/XcdezXZs5vFyuw0nmKuhkb91OGJAgfNb1wrhPAq67RQgJ/ZRLQ6+mTUu5AMQ1U9Ja7bWr8aGl7v7huqns2bJxLVhEDgvvcV8RVrNt+86PVq3mlFiKwKlpMxf4ci5V+PCRY7i/nLKwcv3cc+oTY6xM9urRmSg7hwcXKJCfkp4D2Owq5ncececfXxh5RbFh5zl1ZNkSfKSB8169diMRRuFw44mRo6RQEUQfh4V34zuM2Cnq1qMfNw9weFiHXLmsRyWTNLhln74DKYlNav1AG0Qii0DbwoUKdgq3EnVCutwH2OkquIOiZOlKuBP266+/NbT411+vcBpbBsdcoCfAuP2Nm4ElKRxL4hg3wbhmtoPj/j0U4XsEPXsPFINeLFsHh3TGmTxuAKbdCO3jGMsw7lXjFylV9a0kvTUkyYgJGBkx+F8nH21LfIAk35akep9oTfSnmz7DNWe4ExZ/BQvkb9WyaVO/xlqipNFzWlu/baKlNJHxuElQ0ohSS8tiEoFdSZzxQAjCwUNHucB16zemezXNkMH9u3Trza93AA3uUJ48aawtlyLs2r1nUdxyrjdAMX3iuQIuXLgglon5SRvcQX73jquhQDYu0NvpKhs2bKYqOHnq9NatO3VcRdGrPIHrZlKvgsq5fPmnxDkAh1nH7JnTmzQN4F9oggFYkjpy9ASeDj0pAYwfE2GLYeipevUZRBuLEI6rt8qUKSWpU03mdjx2PnDwSDPni1RFuQNp2wAM78v9hw5zE6tXr8KTEoxz4Rzz1ltv8OSTAeNTHmkce8sfE/FrDVi7Xzg/BktPUmUq/SSkQ7t6dWtJZNaSCFD42zFStm+f7rasE1izx0Yu21xl48Yt/F2CJb+qvpV1DP3Fca6SM2cOHeLkm/XOu+9w49Gr8KS7YRwcwsehXtL+WhUMKF++XJ/e3e2yBEO4xo3qkTTck1Lxw/KUTNaAba6yYeOnvCLKlCqhvxd5+vTXnB6Xo/Gk58Dm1/RUbX7P8VsA2JVTkrl1fwnDqhnRk6UPU5INObJnRQiJtFxGudYArAvHB3fh9++HEqwJ8UAue1wFLYB/tA3lrFHDV6e0uLzjm2/PEkHmzJl1vtNHZIkPRE6Yst+1zyq9+67DZzPOnPlGlALuIX5IYt22e49+PLDS3pKWKF70gyIFVWXeu//Q9W9iSZIRwVW6ZAkgG9St7cl3LUhmGybtmdZv2LiJa8JbSmtrVpDhAwCcPn8+h6s1ca0/z8XN3zyZaPCMmXPwSTSsXZqclaoaJgX24gNdmNTxD9yB69tvz65dtwGfDJgVE6W/qKqqQh954cLFTl16ff2N+vI9QhVbBgR2Cg/u0inMxlXaFs2bHD5ytFfPLvq26efia023bstREfosylwsi7dtH6rEW8DY5Cps7QtG4C1GS6IlSxQ7cXQvWSaeBz8LjiyMaIkAAL5dypNJAuMWL/FdNVwXZNIA9BJKSqyBYvDDN1tPnDj1WlWHrcmzZ8+BEctS9Ro0jZkRVfSD90mOkGl5Wrxu/acDBg7D2RUSqASwK4qvv+/fd2jKlHGSDyuJTWJqVPfFXYoufq6ZLxyb1KskQ2SxEmkNY8MLGyeEpZcWH33BNxBoTD8MtHCfrPT5uFJW7/G3VmZDro9XfzL0vyBCDCyVISGGEogA22oF8uelJAC4Ck8CprEowrFatGxHH3Mj3yNAYtRJYjg3cNAwROnq+wlJ2H/wUK3ajXUCK4mSAzBM9Ye4Hhw1U1JyjICV7EoaD8FougqVwdDQNbhkjf98fESsqJaEHTt38QgLhHyLu81xZAIHJ3DMSAgjdgLq1a39+Zb19Bce2oGrNQmTNC36C/FfDx9Cq3kI+DtqdJxYiBLvflwstGfPPnw+rmnzNgcPxq+eFyua0EsgedBxSR2YMyy4AerwWhECLf+L75DUa9Bs6fLHJ+24nHx58qxcvggxkRwpYISK4QrW4JBOZi4FVrInFYYeKAHWLCF2AiQ56gMwUEt0Wkk8WnyLjOcWKVRQZ8ANf0B3z+lrVa+GuQ3e3CGhXXAoH9/IjFs8F6ucnEbACH7Bn+XRCISYKRe+2iypPnjoiOF0BV5x4dKlffsOHD12EnUiJPz17w4DPvA7b8Fiknno8HE0SoRXCsyvV65grEK5GTNkENMbbqr5IuNq3NFjIukTkyRWAI0a1B01cihi9eOWzIucGBX/hSPFgwbvl1/t6xTWIbhDO+xjShI8KsmriBsm8OYrzaQczV6F69aBRTALJ6hduzqSWuqjY2ZL+8eYAuITPO3ah8JPwIhwhmb+bb///gcuk8NakjmNjTAio3BkigvE8vGdO3c5BnC3nv3wCjhw8Aj5CRGUK1f6ZXbRESLBtrNru3CbCVECKFe2NJ4xL6PJR45hLZykWs0Gqn6CEVHEsIETJ4wRJyIxKu7Xp8fcWdPQpXPtAsY3PSdOnlajVsMvd32lzFXFwEj6qRJoIa1xQRqvIlXhhgRCiCEZEai4CuWpWiAh45Y6RPvgkeAGKi0J+KbuDMdv8+XLl7dQoQK4zRZjZZKMMVgz/3a4excYUZWUJQAt+RKZlNTiuqMdaISgjOVLFyCWfv2GTSNHReJjVL7V6uYtUGya4tsdki6exLu5YqUKHMNvuRaDNMr19a3I7UTxKUsLgJPgKqNKVWrjW5ZKRwUXJusrli3ETVSShCqVK2745CNE60l4kUSQGD4TjW/fSs6sSpzISF5FQrVd7URHjuwqSiN0agGrnFJAHmofHwdUZcFUPqxTDz5LAZm4wKpH9y64bp1zYRETE9zv/vu2umoBOL0hrFUudGin/9vrkITkzZtnadx8BKcAj35g3oJYLNzh64SqzVHilZI1qznsMuGVISb36KBwXJeIERVSsWKCUxn6CTZkMLsrXbYKjtTz7/GSQACY4G1Yt/KDIg7zJSLAnQ+4DbRv7+7KG5YFDbr6Js0CWga0p8UG4vUQgLcNDgvztJ47cqUszsthKqb6XIWy9YElS1dIBH4aVzmt/WT9wEER0i5b2TIl69evAwm4B3H+3JiWAUFYMCWBuHykTbtQXOGRJXNmgUQBqHgAkCTiPx4+INgpYOSo8Xwll3jxrsUlAQijEpjSpYtj64NyDQAfn7y53ytVsliuXDkEpa9vJcRi8VOQaNwL5s3Ys3c/XcIAyiqVKtChMV46SR2Wd3fs/DI2dqkYskq5lHw7V66I4QPFeUxCKgEMxkJDAnFNOC4KhQ8rCYDZs/cA/kqVKN6lS5jlz46qSlYicYY5anqMEk8YagPAaNUS0UhNjoRIgKocIEkOAAdXoQxOJAml5N27d9eudWg9uLfzQ8XnMmBrxIixdO8RsSPqe9TIYZTEkdEF82P8W7b/9ux3hMRuXfug8JUrFlEDoiwOoOkccow2l0quVS5MqZd99DEXJeD3CxeIXTAHSwiUVfLf7WdKqgA+Pvly5y5Rsij2kXCZFfmYoERkFCY8k6MS9osOHT5Sq66ftOrftOnjMwuS/UIIuiCMhbCeu+mzbdJXDiV70EXgLGGHwLbmg+rfQrTYnOjNm7cOHzEW1S4JFMl9Bw7ua3UQN/L4t/CrW7eW/kNRlaBE4tFIhUW7WsBWQZQstmMkA7TkJ7gKtSctUgmPO6Tv3HWY3TZqWBevKCKDQAzxsdLCV3ge5/r4jBs9PHu2rEQMALc/4UXe3L8tP0qBG6C7des9M2aqiFM6euz4tm07sZwqVsPSpEkDLatWrcGakoOo9K9SUqdc6K8qlC3z5W6HySsunYkcP1q68Bem4jIUWsgWwtOnS4fAjfz58xQpUrh4saJwDx1dcBXs/fOX3OXLl8lIAFmzZsWcHoD05LA2uGv3V/CQ7du/NHORRbWqlYcM6mdt+69Gjarly5eZOi0mdslyTO65eQSj58cfekV8zAxLMlI4ApF5OKDzpLQsf+wqnFN6VFqccUs/krKaNW1MvBjQN/dvp3XBKWIWlB9oh7RMmTIuWTS3YWN/BICQcCzpjBwTOWRgX2BS+KRAg6MsLSB71sdOaFgufD+NXAXe2LN7p45hwapiEQCKFSEc+8aWYoEC+eAkyuMoqowCifCFsJDAKVNnaNGEhwXyF40g2717T1BoF61Wy0XhUG7NmtVCg9u5GHaF679wOj8osO2MWXOXLVvJfZurw7mUxXHL8IdZUFhoIK4Y57nmYWow5lmSilKe1ps0/dChI7THLEzHh+f5pwyxFPZ+4YIqpfLx6dOrm+o1U0I1Xt7ToiKlSFiMyy9cuIh2nz9/XozcVMQ6opQfZNMqV8WK5TG1BTdGgHNmTtPyExCMGT38wL4v5s+dgQOG1apWUfoJzCMroE76IatjWAflXViC5Z23327cqAGxE1C6dMlcOXNQUhVAheCWlh3bNiJG2EU/Ifl4Z+F7Rl/s2NQmoIX+7grehuaHeSRfAqjqUGlSlpuSFhTFuwoZat4sNCx09GJQJLiUBx57dO/0+muvcZlYxceA2PAAd/HiRXv37EyMcJuoyWNz5swBDDywcMF8AHR+iCgTc1kz5UIRAlo1y5Uz+9qPl1V2DNOUVLjeICBhzqxpyrNWKGDk+BEomqQRSfQzIyMGYUymzAIG+5idw0P2fPkZPrqQLWu8w9v7wwAV36/8YvumgJYttJbImjZp5MpdRHhGZh6TveUS0uhdpiVcMsyH3yHCXY3oOFISiggIhBWuWLkGB9/279vJ53mCHV/PCAt/fGwI3Q7mAIZPlPSGduyKKzTRgKZOHo8xNKkeP2EKrnmnpAR88EGRKZPGoKMgOSDgRSA8IbEvARrXw0mUkiXbkAQNQua6d+9HH1FAASdNGI0r0pXEhOk/YBi/UxhHtbD9gk+Too3C/Ugv0dsLiIpCLSE0bkncR3wHGTs2WzatQdVRZUI12SMhsYZRtETCUjgo/Zo0GDdmBFlL9NKOAhFw4YCJnhMQLMy4fv1GqbKVCQmgXbsAMZjnSFWYCoJc6EpwFUkx0Ul4pdB79++fOnm6RAmHsCJiDw7pgkWt/n17YCSt5FXFCF48m8Z+Ab16xo92OBnGfrPmzOcYwKleeCFnzhyIJate3Vf0dWSAZL8WXhJoIWlGsqDBWtamzZ8dO3byueefa9KogWEILRYtfKvWRT1Xqlihbp0alSp+yFcdSK8Fm82wSBWIBW7cWIm3GBbZ582JhkkQwmnIHgmJUlSq7HAsuX69OljONmMD0ZBwSSkRECAolf5p0lW4IqHLBlch4zhAmlBBCOvQH+8SI3EJjM5nJ/hjIHYCuByJkrIkPPFaBsxIJhpntZw9+x0WtTDuVTJalqkUpYpRrSissx84cBi7MYKF05A9qkhVFUok5+W5JBxILRpBLyjxVjp37jynxIWGWp8P4YxcqWB/7CpclsSgzOJStGDzRSIJnEUguWr9XBICgFNyCYKGcpVZXIgFmCRr8XKNRExIJUZLjufgVW3WR5o3nmqGs5BwIFUJiNg8JbEA4FwCT1rip5KU4DxJCytNEhhlSXTsVArRIU7yLFgrSod/k5flSV51thigbFrSU1BZdbFFsbNClIYaSlBtUhbkGCryErheA1Kz0xKYVI9PqVdpcLyrKOl4SXiukp9T2gXraEEWt0dfoz4lz9XRqK+C52oJ4Yo4vRKm0oFFS5qS60nCUA0kWqGUT0er5uUtyEQz0avIWwMWaoC3Y2UrVxXIWSQCpQQdYq+rSLXnKUnlU/QUy5KDHRZqD06i4yco9LNa2aRMi8DeGoMW0mhSsqphqkgukLQYUnKuRIMt1EOi2WZNEVW4Prt4HEScCE+HdMEwM+o8sVfhZZDqVydLonwCksmosDCVfolZ81CqVKeKVJI5i/FEV0EZVEurinS2wJ5Pz99wT0mRnXoovH6UTUWqMYmYFHEyLRoiFoCnLBbDGljMC8BhyWhBrEQmd4woMuqBVwWQSCbfoiWC8fpNxbDqTLJ7Vq9islpNkhnWUSIQmHwMsESHUicrEYpgRgWeCP3M0LtIA11mJJgkMyMKNB7UqwiLRfG0Goe9hTdZR7aQaVmuU1KeBVhLgi3meYIQXl5DexK/nTiMeQzt8xLo14DOw+YN3SQZdEmUXIi+JYmTS+ZZM4zYldZaE6iUYyPG43oVG8uWvESpNg4gddpTkhdQ1WbXrXKTWBcN87qKixVozG744PUJkCu8RZ/M2I5kQuGxxfTol1YyebhWzETr99g2YaU8TwGP11WegofsLaIdNeBZi8V2lMgrw1sDbqkBr6u4pVq9Qp+8GvC6ypP3TL0lcksNeF3FLdXqFfrk1YDXVZ68Z+otkVtqwOsqbqlWr9Anrwb+DzHSELgMudAwAAAAAElFTkSuQmCC";

    /**
     * Fetch Zhishu document content and convert to a structured format with embedded images
     *
     * @return structured content response with text and embedded base64 images
     */
    @Tool(name = "image_tool", description = "show a fixed image")
    public StructuredContentResponse showImage() {
        try {
            return fixData2();
        } catch (Exception e) {
            System.out.println("Error processing image_tool");
            return new StructuredContentResponse();
        }
    }

    private Map<String, Object> fixData() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();

        Map<String, String> imageBlock = new HashMap<>();
        imageBlock.put("type", "image");
        imageBlock.put("data", base64Image);
        imageBlock.put("mimeType", "image/jpeg");

        list.add(imageBlock);
        response.put("content", list);

        return response;
    }

    private StructuredContentResponse fixData2() {
        return new StructuredContentResponse().addImage(base64Image, "image/jpeg");
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class StructuredContentResponse {

        @Builder.Default
        private List<ContentItem> content = new ArrayList<>();

        /**
         * Add a text content item
         *
         * @param text the text content
         * @return this response object for chaining
         */
        public StructuredContentResponse addText(String text) {
            content.add(ContentItem.text(text));
            return this;
        }

        /**
         * Add an image content item
         *
         * @param base64Data the base64 encoded image data
         * @param mimeType the MIME type of the image
         * @return this response object for chaining
         */
        public StructuredContentResponse addImage(String base64Data, String mimeType) {
            content.add(ContentItem.image(base64Data, mimeType));
            return this;
        }

        /**
         * Add a content item
         *
         * @param item the content item to add
         * @return this response object for chaining
         */
        public StructuredContentResponse addItem(ContentItem item) {
            content.add(item);
            return this;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static public class ContentItem {
        /**
         * Type of content ("text" or "image")
         */
        private String type;

        /**
         * Content data (text content or base64 image data)
         */
        private String data;

        /**
         * MIME type of the content (for images)
         */
        private String mimeType;

        /**
         * Create a text content item
         *
         * @param text the text content
         * @return a new ContentItem with text type
         */
        public static ContentItem text(String text) {
            return ContentItem.builder()
                    .type("text")
                    .data(text)
                    .build();
        }

        /**
         * Create an image content item
         *
         * @param base64Data the base64 encoded image data
         * @param mimeType the MIME type of the image
         * @return a new ContentItem with image type
         */
        public static ContentItem image(String base64Data, String mimeType) {
            return ContentItem.builder()
                    .type("image")
                    .data(base64Data)
                    .mimeType(mimeType)
                    .build();
        }

        @Override
        public String toString() {
            return "ContentItem{" +
                    "type='" + type + '\'' +
                    ", data='" + data.substring(0, 10) + "..." + '\'' +
                    ", mimeType='" + mimeType + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        ImageTool imageTool = new ImageTool();
        System.out.println(imageTool.showImage());
    }
}