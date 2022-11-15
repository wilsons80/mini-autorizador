package br.com.vr.build;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import br.com.vr.entitys.Cartao;
import br.com.vr.vo.CartaoVO;

@Component
public class CartaoMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Converter<CartaoVO, Cartao> converter() {
		return ctx -> ctx.getSource() == null ? null : cartaoModalMapper().map(ctx.getSource(), Cartao.class);
	}
	
	@Bean
	public ModelMapper cartaoModalMapper() {
		ModelMapper modalMapper = new ModelMapper();
		modalMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		PropertyMap<CartaoVO, Cartao> cartaoMap = new PropertyMap<CartaoVO, Cartao>() {
			@Override
			protected void configure() {
				map().setId(null);
				map().setNumeroCartao(source.getNumeroCartao());
				map().setSenha(source.getSenha());
			}
		};		
		modalMapper.addMappings(cartaoMap);
		return modalMapper;		
	}
	
	public CartaoVO toModel(Cartao cartao) {
		return modelMapper.map(cartao, CartaoVO.class);
	}
	
	public Cartao toEntity(CartaoVO cartaoVo) {
		return modelMapper.map(cartaoVo, Cartao.class);
	}

	
}
